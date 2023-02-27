package dev.dumble.homeproject.HomeProject_PhaseIV.controller;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.transactions.Offer;
import dev.dumble.homeproject.HomeProject_PhaseIV.mapper.OfferDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.OfferService;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.RequestService;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.SpecialistService;
import dev.dumble.homeproject.HomeProject_PhaseIV.utils.enums.RequestSorter;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/offer")
public class OfferController {

	private OfferService offerService;
	private RequestService requestService;
	private SpecialistService specialistService;

	@PostMapping("/create")
	public ResponseEntity<Offer> createOffer(@RequestBody OfferDTO offerDTO) {
		var offer = offerDTO.toOffer();

		var specialist = specialistService.findById(offerDTO.getSpecialistId());
		var request = requestService.findById(offerDTO.getRequestId());

		offer.setRequest(request);
		offer.setSpecialist(specialist);

		var databaseOffer = offerService.create(offer, request, specialist);
		var optionalRequest = Optional.ofNullable(databaseOffer);

		request.addOffer(databaseOffer);
		requestService.update(request);

		return ResponseEntity.of(optionalRequest);
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
