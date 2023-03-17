package dev.dumble.homeproject.HomeProject_PhaseIV.controller;

import dev.dumble.homeproject.HomeProject_PhaseIV.dto.OfferDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.users.Client;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.users.Specialist;
import dev.dumble.homeproject.HomeProject_PhaseIV.filter.enums.RequestSorter;
import dev.dumble.homeproject.HomeProject_PhaseIV.mapper.OfferMapper;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.impl.OfferService;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.impl.RequestService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/offer")
public class OfferController {

	private OfferService offerService;
	private RequestService requestService;

	@PostMapping("/create")
	@PreAuthorize("hasRole('SPECIALIST')")
	public void createOffer(@RequestBody @Valid OfferDTO offerDTO,
							@RequestParam(value = "request_id") Long requestId) {
		var offer = OfferMapper.getInstance().map(offerDTO);

		var specialist = (Specialist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		var request = requestService.findById(requestId);

		offer.setRequest(request);
		offer.setSpecialist(specialist);

		var databaseOffer = offerService.create(offer, request, specialist);
		request.addOffer(databaseOffer);
		requestService.update(request);
	}

	@GetMapping("/all-offers")
	@PreAuthorize("hasRole('CLIENT')")
	public ResponseEntity<Set<OfferDTO>> findAllOffersFromRequest(@RequestParam(value = "request_id") Long requestId) {
		var request = requestService.findById(requestId);
		var client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		var sortedOffers = offerService.findRequestOffers(client, request, RequestSorter.SORT_NONE);
		var optionalOffers = Optional.of(sortedOffers);

		return ResponseEntity.of(optionalOffers);
	}

	@GetMapping("/sort-price")
	@PreAuthorize("hasRole('CLIENT')")
	public ResponseEntity<Set<OfferDTO>> sortRequestOffersByPrice(@RequestParam(value = "request_id") Long requestId) {
		var request = requestService.findById(requestId);
		var client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		var sortedOffers = offerService.findRequestOffers(client, request, RequestSorter.SORT_PRICE);
		var optionalOffers = Optional.of(sortedOffers);

		return ResponseEntity.of(optionalOffers);
	}

	@GetMapping("/sort-rating")
	@PreAuthorize("hasRole('CLIENT')")
	public ResponseEntity<Set<OfferDTO>> sortRequestOffersBySpecialistRating(@RequestParam(value = "request_id") Long requestId) {
		var request = requestService.findById(requestId);
		var client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		var sortedOffers = offerService.findRequestOffers(client, request, RequestSorter.SORT_SPECIALIST_RATING);
		var optionalOffers = Optional.of(sortedOffers);

		return ResponseEntity.of(optionalOffers);
	}
}
