package dev.dumble.homeproject.HomeProject_PhaseIV.service;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.members.Client;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.members.Specialist;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.services.Assistance;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.transactions.Offer;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.transactions.Request;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.transactions.Review;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.enums.RequestStatus;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.enums.SpecialistStatus;
import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.*;
import dev.dumble.homeproject.HomeProject_PhaseIV.repository.IRequestRepository;
import dev.dumble.homeproject.HomeProject_PhaseIV.utils.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RequestService {

	private final ClientService clientService;
	private final SpecialistService specialistService;
	private final AssistanceService assistanceService;
	private final ReviewService reviewService;

	private final IRequestRepository repository;

	public Request create(Request request, Assistance assistance, Client client) {
		if (request.getDescription().isBlank() || request.getAddress().isBlank())
			throw new MissingInformationException();

		if (request.getOfferedPrice() < assistance.getMinimumPrice())
			throw new InsufficientFundsException();

		if (request.getDueTime().isBefore(LocalDateTime.now()))
			throw new InvalidDateException();

		if (this.exists(request))
			throw new DuplicateEntityException();

		var assistanceId = assistance.getId();
		if (assistanceId == null || assistanceService.findById(assistanceId) == null)
			throw new InvalidEntityException();

		var clientId = client.getId();
		if (clientId == null || clientService.findById(clientId) == null)
			throw new InvalidEntityException();

		request.setStatus(RequestStatus.AWAITING_SUGGESTION);
		request.setAssistance(assistance);
		request.setClient(client);
		var savedRequest = repository.save(request);

		client.addRequest(savedRequest);
		clientService.update(client);

		return savedRequest;
	}

	public Request confirmRequestStarted(Request request, Offer offer) {

		if (offer == null)
			throw new InvalidEntityException();

		var startTime = offer.getStartTime();
		if (startTime == null || startTime.isBefore(Utility.getPresentDate()))
			throw new InvalidDateException("The request hasn't been started because the specialist needs more time.");

		request.setStatus(RequestStatus.STARTED);
		return this.update(request);
	}

	public Request finishRequest(Request request) {
		if (request == null)
			throw new InvalidEntityException();

		if (request.getStatus() != RequestStatus.STARTED)
			throw new NotPermittedException();

		var dueTime = request.getDueTime();
		var now = LocalDateTime.now();

		if (now.isAfter(dueTime)) {
			var delayedDays = ChronoUnit.HOURS.between(dueTime, now);
			var specialist = request.getAcceptedOffer().getSpecialist();

			specialist.reduceRating((int) delayedDays);
			if (specialist.getRating() < 0)
				specialist.setStatus(SpecialistStatus.DISABLED);

			specialistService.update(specialist);
		}

		request.setStatus(RequestStatus.DONE);
		return this.update(request);
	}

	public Request payByCredit(Request request, Client client, Review review) {
		var offer = request.getAcceptedOffer();
		if (client == null || offer == null) throw new InvalidEntityException();

		var specialist = offer.getSpecialist();
		if (specialist == null) throw new InvalidEntityException();

		var rating = review.getRating();
		if (rating < 0 || rating > 5)
			throw new InvalidRatingException();

		if (request.getStatus() != RequestStatus.DONE)
			throw new NotPermittedException();

		this.paySpecialistByCredit(client, specialist, offer.getOfferedPrice().intValue());
		reviewService.addReviewSpecialist(specialist, rating, review.getDescription());

		request.setStatus(RequestStatus.PAID);
		return this.update(request);
	}

	public Request payOnline(Request request, Client client) {
		var offer = request.getAcceptedOffer();
		if (client == null || offer == null) throw new InvalidEntityException();

		var specialist = offer.getSpecialist();
		if (specialist == null) throw new InvalidEntityException();

		if (request.getStatus() != RequestStatus.DONE)
			throw new NotPermittedException();

		this.paySpecialistOnline(client, specialist, offer.getOfferedPrice().intValue());

		request.setStatus(RequestStatus.PAID);
		return this.update(request);
	}

	public List<Request> findMatchingRequests(Specialist specialist) {
		return repository.findMatchingRequests(new HashSet<>(specialist.getAssistanceList()));
	}

	public Set<Request> findClientRequests(Client client, RequestStatus status) {
		return repository.searchForOffers(status.name(), client.getId());
	}

	public Request read(Request request) {
		return repository.findOne(Example.of(request)).orElseThrow(InvalidEntityException::new);
	}

	public Request update(Request request) {
		var id = request.getId();
		if (id == null)
			throw new InvalidEntityException();

		if (repository.findById(id).isEmpty())
			throw new InvalidEntityException();

		return repository.save(request);
	}

	public void delete(Request request) {
		repository.delete(request);
	}

	public boolean exists(Request request) {
		return repository.exists(Example.of(request));
	}

	public Request findById(Long id) {
		return repository.findById(id).orElseThrow(InvalidEntityException::new);
	}

	public void paySpecialistByCredit(Client client, Specialist specialist, int offeredPrice) {
		var credit = client.getCredit();

		if (credit < offeredPrice)
			throw new InsufficientFundsException("You don't have enough credit for this payment");

		client.setCredit(credit - offeredPrice);
		clientService.update(client);

		specialist.setCredit(specialist.getCredit() + offeredPrice);
		specialistService.update(specialist);
	}

	public void paySpecialistOnline(Client client, Specialist specialist, int offeredPrice) {
		var credit = client.getCredit();

		if (credit < offeredPrice)
			throw new InsufficientFundsException("You don't have enough credit for this payment");

		client.setCredit(credit - offeredPrice);
		clientService.update(client);

		specialist.setCredit(specialist.getCredit() + (offeredPrice * 70L / 100));
		specialistService.update(specialist);
	}
}
