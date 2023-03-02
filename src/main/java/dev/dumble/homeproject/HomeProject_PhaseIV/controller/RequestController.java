package dev.dumble.homeproject.HomeProject_PhaseIV.controller;

import dev.dumble.homeproject.HomeProject_PhaseIV.dto.CardDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.dto.RequestDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.dto.ReviewDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.transactions.Request;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.users.Client;
import dev.dumble.homeproject.HomeProject_PhaseIV.mapper.RequestMapper;
import dev.dumble.homeproject.HomeProject_PhaseIV.mapper.ReviewMapper;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.impl.AssistanceService;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.impl.CaptchaService;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.impl.OfferService;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.impl.RequestService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j @RestController
@AllArgsConstructor
@RequestMapping("/api/v1/request")
public class RequestController {

	private RequestService requestService;
	private OfferService offerService;
	private AssistanceService assistanceService;
	private CaptchaService captchaService;

	@PostMapping("/create")
	public ResponseEntity<Request> createRequest(@RequestBody @Valid RequestDTO requestDTO,
												 @RequestParam(value = "assistance_id") Long assistanceId) {
		var request = RequestMapper.getInstance().map(requestDTO);

		var assistance = assistanceService.findById(assistanceId);
		var client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		requestService.create(request, assistance, client);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping("/{id}/accept-offer")
	public ResponseEntity<Request> acceptRequestOffer(@PathVariable(name = "id") Long requestId,
													  @RequestParam(value = "offer_id") Long offerId) {
		var databaseRequest = requestService.findById(requestId);
		var databaseOffer = offerService.findById(offerId);
		var client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		offerService.acceptOffer(client, databaseRequest, databaseOffer);

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PostMapping("/{id}/start-request")
	public ResponseEntity<Request> startRequest(@PathVariable(name = "id") Long requestId) {
		var databaseRequest = requestService.findById(requestId);
		var client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		requestService.startRequest(client, databaseRequest);

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PostMapping("/{id}/finish-request")
	public ResponseEntity<Request> finishRequest(@PathVariable(name = "id") Long requestId) {
		var request = requestService.findById(requestId);
		var client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		requestService.finishRequest(client, request);

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PostMapping("/{id}/credit-payment")
	public ResponseEntity<Request> creditPaymentForRequest(@RequestBody @Valid ReviewDTO reviewDTO,
														   @PathVariable(name = "id") Long requestId) {
		var review = ReviewMapper.getInstance().map(reviewDTO);

		var request = requestService.findById(requestId);
		var client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		requestService.paySpecialist(request, client, review);

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PostMapping("/{id}/online-payment")
	public ResponseEntity<Request> onlinePaymentForRequest(@RequestBody @Valid CardDTO cardDTO,
														   @PathVariable(name = "id") Long requestId) {
		captchaService.validateCaptcha(cardDTO.getCaptchaResponse());

		var request = requestService.findById(requestId);
		var client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		requestService.paySpecialist(request, client);

		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
