package dev.dumble.homeproject.HomeProject_PhaseIV.service.impl;

import dev.dumble.homeproject.HomeProject_PhaseIV.dto.OfferDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.transactions.Offer;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.transactions.Request;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.users.Client;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.users.Specialist;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.enums.RequestStatus;
import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.*;
import dev.dumble.homeproject.HomeProject_PhaseIV.filter.enums.RequestSorter;
import dev.dumble.homeproject.HomeProject_PhaseIV.mapper.OfferMapper;
import dev.dumble.homeproject.HomeProject_PhaseIV.repository.IOfferRepository;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.GenericService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OfferService extends GenericService<Long, IOfferRepository, Offer> {

	private final SpecialistService specialistService;
	private final RequestService requestService;

	public OfferService(IOfferRepository repository, SpecialistService specialistService, RequestService requestService) {
		super(repository);
		this.specialistService = specialistService;
		this.requestService = requestService;
	}

	public Offer create(Offer offer, Request request, Specialist specialist) {
		if (specialist.isNotAccepted())
			throw new NotPermittedException("The specialist hasn't been accepted yet.");

		var status = request.getStatus();
		if (status != RequestStatus.AWAITING_SUGGESTION && status != RequestStatus.AWAITING_SELECTION)
			throw new InvalidRequestStatusException();

		var assistance = request.getAssistance();
		if (!specialist.containsAssistance(assistance))
			throw new InvalidProfessionException();

		var offeredPrice = offer.getOfferedPrice();
		if (offeredPrice < assistance.getMinimumPrice())
			throw new InsufficientFundsException("You haven't offered enough money -> %s$".formatted(offeredPrice));

		if (super.exists(offer))
			throw new DuplicateEntityException();

		offer.setCreationTime(LocalDateTime.now());
		var savedOffer = super.getRepository().save(offer);

		request.setStatus(RequestStatus.AWAITING_SELECTION);
		requestService.update(request);

		specialist.incrementSubmittedOffers();
		specialistService.update(specialist);

		return savedOffer;
	}

	@Override
	public boolean exists(Offer offer) {
		var description = offer.getDescription();
		var requestId = offer.getRequest().getId();
		var specialistId = offer.getSpecialist().getId();

		return super.getRepository()
				.findMatchingOffers(description, requestId, specialistId)
				.isPresent();
	}

	public Set<OfferDTO> findRequestOffers(Client client, Request request, RequestSorter sorter) {
		if (!client.isVerified())
			throw new NotPermittedException("The clients account hasn't been verified yet.");

		var requestId = request.getId();

		var offerSet =  switch (sorter) {
			case SORT_NONE -> request.getOffers().stream()
					.map(offer -> OfferMapper.getInstance().serialize(offer))
					.collect(Collectors.toSet());

			case SORT_PRICE -> super.getRepository().findRequestOffersByPrice(requestId);
			case SORT_SPECIALIST_RATING -> super.getRepository().findRequestOffersBySpecialistRating(requestId);
		};

		return offerSet.stream()
				.map(offer -> OfferMapper.getInstance().serialize((Offer) offer))
				.collect(Collectors.toSet());
	}

	public void acceptOffer(Client client, Request request, Offer offer) {
		if (!client.isVerified())
			throw new NotPermittedException("The clients account hasn't been verified yet.");

		if (request.getStatus() != RequestStatus.AWAITING_SELECTION)
			throw new InvalidRequestStatusException("The requests status must be awaiting selection for it to be started.");

		request.setStatus(RequestStatus.AWAITING_START);
		request.setAcceptedOffer(offer);

		requestService.update(request);
	}
}
