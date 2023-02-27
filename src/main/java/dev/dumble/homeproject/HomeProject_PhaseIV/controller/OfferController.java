package dev.dumble.homeproject.HomeProject_PhaseIV.controller;

import dev.dumble.homeproject.HomeProject_PhaseIV.dto.OfferDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.transactions.Offer;
import dev.dumble.homeproject.HomeProject_PhaseIV.mappers.OfferMapper;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.impl.OfferService;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.impl.RequestService;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.impl.SpecialistService;
import dev.dumble.homeproject.HomeProject_PhaseIV.utils.enums.RequestSorter;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@Slf4j @RestController
@AllArgsConstructor
@RequestMapping("/api/v1/offer")
public class OfferController {

	private OfferService offerService;
	private RequestService requestService;
	private SpecialistService specialistService;

	@PostMapping("/create")
	public ResponseEntity<Offer> createOffer(@RequestBody @Valid OfferDTO offerDTO) {
		var offer = OfferMapper.getInstance().map(offerDTO);

		var specialist = specialistService.findById(offerDTO.getSpecialistId());
		var request = requestService.findById(offerDTO.getRequestId());

		offer.setRequest(request);
		offer.setSpecialist(specialist);

		var databaseOffer = offerService.create(offer, request, specialist);
		request.addOffer(databaseOffer);
		requestService.update(request);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/all-offers")
	public ResponseEntity<Set<Offer>> findAllOffersFromRequest(@RequestParam(value = "request_id") Long requestId) {
		var request = requestService.findById(requestId);

		var sortedOffers = offerService.findRequestOffers(request, RequestSorter.SORT_NONE);
		var optionalOffers = Optional.of(sortedOffers);

		return ResponseEntity.of(optionalOffers);
	}

	@GetMapping("/sort-price")
	public ResponseEntity<Set<Offer>> sortRequestOffersByPrice(@RequestParam(value = "request_id") Long requestId) {
		var request = requestService.findById(requestId);

		var sortedOffers = offerService.findRequestOffers(request, RequestSorter.SORT_PRICE);
		var optionalOffers = Optional.of(sortedOffers);

		return ResponseEntity.of(optionalOffers);
	}

	@GetMapping("/sort-rating")
	public ResponseEntity<Set<Offer>> sortRequestOffersBySpecialistRating(@RequestParam(value = "request_id") Long requestId) {
		var request = requestService.findById(requestId);

		var sortedOffers = offerService.findRequestOffers(request, RequestSorter.SORT_SPECIALIST_RATING);
		var optionalOffers = Optional.of(sortedOffers);

		return ResponseEntity.of(optionalOffers);
	}
}
