package dev.dumble.homeproject.HomeProject_PhaseIV.service;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.members.Specialist;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.transactions.Offer;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.transactions.Request;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.enums.RequestStatus;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.enums.SpecialistStatus;
import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.*;
import dev.dumble.homeproject.HomeProject_PhaseIV.repository.IOfferRepository;
import dev.dumble.homeproject.HomeProject_PhaseIV.utils.Utility;
import dev.dumble.homeproject.HomeProject_PhaseIV.utils.enums.RequestSorter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OfferService {

	private final IOfferRepository repository;

	private final RequestService requestService;

	public Offer create(Offer offer, Request request, Specialist specialist) {
		var specialistStatus = specialist.getStatus();
		Utility.checkSpecialistStatus(
				specialistStatus != SpecialistStatus.DISABLED &&
						specialistStatus != SpecialistStatus.PENDING_CONFIRMATION);

		var assistance = request.getAssistance();
		var assistanceOptional = specialist.getAssistanceList()
				.stream()
				.filter(optionalAssistance -> optionalAssistance.getName().equalsIgnoreCase(assistance.getName()))
				.findAny();

		if (assistanceOptional.isEmpty())
			throw new InvalidProfessionException();

		var status = request.getStatus();
		if (status != RequestStatus.AWAITING_SUGGESTION && status != RequestStatus.AWAITING_SELECTION)
			throw new InvalidRequestStatusException();

		this.validateOffer(offer, assistance.getMinimumPrice());

		var savedOffer = repository.save(offer);
		savedOffer.setCreationTime(Utility.getPresentDate());

		request.setStatus(RequestStatus.AWAITING_SELECTION);
		requestService.update(request);

		return savedOffer;
	}

	public void validateOffer(Offer offer, Long minimumPrice) {
		if (offer.getOfferedPrice() < minimumPrice)
			throw new InsufficientFundsException();

		if (offer.getStartTime().isBefore(LocalDateTime.now()))
			throw new InvalidDateException();

		if (offer.getDescription().isBlank())
			throw new MissingInformationException();

		if (this.exists(offer))
			throw new DuplicateEntityException();
	}

	public Offer read(Offer offer) {
		return repository.findOne(Example.of(offer)).orElseThrow(InvalidEntityException::new);
	}

	public Offer update(Offer offer) {
		var id = offer.getId();
		if (id == null)
			throw new InvalidEntityException();

		if (repository.findById(id).isEmpty())
			throw new InvalidEntityException();

		return repository.save(offer);
	}

	public void delete(Offer offer) {
		repository.delete(offer);
	}

	public boolean exists(Offer offer) {
		var description = offer.getDescription();
		var requestId = offer.getRequest().getId();
		var specialistId = offer.getSpecialist().getId();

		return repository
				.findMatchingOffers(description, requestId, specialistId)
				.isPresent();
	}

	public Set<Offer> findRequestOffers(Request request, RequestSorter sorter) {
		if (request == null) throw new InvalidEntityException();

		var status = request.getStatus();
		if (status != RequestStatus.AWAITING_SUGGESTION && status != RequestStatus.AWAITING_SELECTION)
			throw new InvalidRequestStatusException();

		var requestId = request.getId();
		if (requestId == null) throw new InvalidEntityException();

		return switch (sorter) {
			case SORT_PRICE -> repository.findRequestOffersByPrice(requestId);
			case SORT_SPECIALIST_RATING -> repository.findRequestOffersBySpecialistRating(requestId);
		};
	}

	public Request acceptOffer(Request request, Offer offer) {
		if (request == null || offer == null)
			throw new InvalidEntityException();

		offer.setAcceptedRequest(request);
		var savedOffer = this.update(offer);

		request.setAcceptedOffer(savedOffer);
		request.setStatus(RequestStatus.AWAITING_START);
		request.addOffer(savedOffer);

		return requestService.update(request);
	}

	public Offer findById(Long id) {
		return repository.findById(id).orElseThrow(InvalidEntityException::new);
	}
}
