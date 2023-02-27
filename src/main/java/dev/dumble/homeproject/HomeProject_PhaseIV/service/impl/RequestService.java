package dev.dumble.homeproject.HomeProject_PhaseIV.service.impl;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.members.Client;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.members.Specialist;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.services.Assistance;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.transactions.Offer;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.transactions.Request;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.transactions.Review;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.enums.RequestStatus;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.enums.SpecialistStatus;
import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.DuplicateEntityException;
import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.InsufficientFundsException;
import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.InvalidEntityException;
import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.NotPermittedException;
import dev.dumble.homeproject.HomeProject_PhaseIV.repository.IRequestRepository;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.GenericService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;

@Service
public class RequestService extends GenericService<Long, IRequestRepository, Request> {

	private final ClientService clientService;
	private final SpecialistService specialistService;
	private final ReviewService reviewService;

	public RequestService(IRequestRepository repository, ClientService clientService, SpecialistService specialistService, ReviewService reviewService) {
		super(repository);
		this.clientService = clientService;
		this.specialistService = specialistService;
		this.reviewService = reviewService;
	}

	public Request create(Request request, Assistance assistance, Client client) {
		var minimumPrice = assistance.getMinimumPrice();
		if (request.getOfferedPrice() < minimumPrice)
			throw new InsufficientFundsException("The offered price is lower than the minimum price -> %s$".formatted(minimumPrice));

		if (this.exists(request))
			throw new DuplicateEntityException();

		request.setStatus(RequestStatus.AWAITING_SUGGESTION);
		request.setAssistance(assistance);
		request.setClient(client);
		var savedRequest = super.getRepository().save(request);

		client.addRequest(savedRequest);
		clientService.update(client);

		return savedRequest;
	}

	public void startRequest(Request request) {
		if (request.getStatus() != RequestStatus.AWAITING_START)
			throw new NotPermittedException("The request cannot be started yet because the client isn't waiting for it to start.");

		request.setStatus(RequestStatus.STARTED);
		this.update(request);
	}

	public void finishRequest(Request request) {
		if (request.getStatus() != RequestStatus.STARTED)
			throw new NotPermittedException("The request can't be started because the status isn't started.");

		var acceptedOffer = request.getAcceptedOffer();
		if (acceptedOffer == null)
			throw new InvalidEntityException("The request hasn't accepted any offers yet.");

		this.handleOverdueRequest(request, acceptedOffer);

		request.setStatus(RequestStatus.DONE);
		this.update(request);
	}

	public void handleOverdueRequest(Request request, Offer acceptedOffer) {
		var dueTime = request.getDueTime();
		var now = LocalDateTime.now();

		if (!now.isAfter(dueTime)) return;
		var delayedDays = ChronoUnit.HOURS.between(dueTime, now);
		var specialist = acceptedOffer.getSpecialist();

		specialist.reduceRating((int) delayedDays);
		if (specialist.getRating() < 0) specialist.setStatus(SpecialistStatus.DISABLED);

		specialistService.update(specialist);
	}

	public void paySpecialist(Request request, Client client) {
		this.paySpecialist(request, client, null);
	}

	public void paySpecialist(Request request, Client client, Review review) {
		if (request.getStatus() != RequestStatus.DONE)
			throw new NotPermittedException("The request can't be paid because the status isn't done.");

		var offer = request.getAcceptedOffer();
		if (offer == null)
			throw new InvalidEntityException("The request hasn't accepted any offers yet.");

		var specialist = offer.getSpecialist();
		this.performTransaction(client, specialist, offer.getOfferedPrice().intValue());

		if (review != null)
			reviewService.addReviewSpecialist(specialist, review.getDescription(), review.getRating());

		request.setStatus(RequestStatus.PAID);
		this.update(request);
	}

	public void performTransaction(Client client, Specialist specialist, Integer offeredPrice) {
		var credit = client.getCredit();

		if (credit < offeredPrice)
			throw new InsufficientFundsException("You don't have enough credit for this payment -> %s$".formatted(offeredPrice));

		client.setCredit(credit - offeredPrice);
		clientService.update(client);

		specialist.setCredit(specialist.getCredit() + (offeredPrice * 70L / 100));
		specialistService.update(specialist);
	}

	public Set<Request> findMatchingRequests(Specialist specialist) {
		return super.getRepository().findMatchingRequests(specialist.getAssistanceList());
	}

	public Set<Request> findClientRequests(Client client, String status) {
		var requestStatus = RequestStatus.from(status);

		return super.getRepository().findClientRequests(requestStatus, client.getId());
	}
}
