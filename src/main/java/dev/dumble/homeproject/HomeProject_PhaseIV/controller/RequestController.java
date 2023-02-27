package dev.dumble.homeproject.HomeProject_PhaseIV.controller;

import dev.dumble.homeproject.HomeProject_PhaseIV.dto.CardDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.dto.RequestDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.dto.ReviewDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.transactions.Request;
import dev.dumble.homeproject.HomeProject_PhaseIV.mappers.RequestMapper;
import dev.dumble.homeproject.HomeProject_PhaseIV.mappers.ReviewMapper;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.impl.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j @RestController
@AllArgsConstructor
@RequestMapping("/api/v1/request")
public class RequestController {

	private RequestService requestService;
	private OfferService offerService;
	private AssistanceService assistanceService;
	private ClientService clientService;
	private CaptchaService captchaService;

	@PostMapping("/create")
	public ResponseEntity<Request> createRequest(@RequestBody @Valid RequestDTO requestDTO) {
		var request = RequestMapper.getInstance().map(requestDTO);

		var assistance = assistanceService.findById(requestDTO.getAssistanceId());
		var client = clientService.findById(requestDTO.getClientId());

		requestService.create(request, assistance, client);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping("/{id}/accept-offer")
	public ResponseEntity<Request> acceptRequestOffer(@PathVariable(name = "id") Long requestId,
													  @RequestParam(value = "offer_id") Long offerId) {
		var databaseRequest = requestService.findById(requestId);
		var databaseOffer = offerService.findById(offerId);

		offerService.acceptOffer(databaseRequest, databaseOffer);

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PostMapping("/{id}/start-request")
	public ResponseEntity<Request> startRequest(@PathVariable(name = "id") Long requestId,
												@RequestParam(value = "offer_id") Long offerId) {
		var databaseRequest = requestService.findById(requestId);
		var databaseOffer = offerService.findById(offerId);

		requestService.startRequest(databaseRequest);

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PostMapping("/{id}/finish-request")
	public ResponseEntity<Request> finishRequest(@PathVariable(name = "id") Long requestId) {
		var request = requestService.findById(requestId);

		requestService.finishRequest(request);

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PostMapping("/{id}/credit-payment")
	public ResponseEntity<Request> creditPaymentForRequest(@RequestBody @Valid ReviewDTO reviewDTO,
														   @PathVariable(name = "id") Long requestId,
														   @RequestParam(value = "client_id") Long clientId) {
		var review = ReviewMapper.getInstance().map(reviewDTO);

		var request = requestService.findById(requestId);
		var client = clientService.findById(clientId);

		requestService.paySpecialist(request, client, review);

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PostMapping("/{id}/online-payment")
	public ResponseEntity<Request> onlinePaymentForRequest(@RequestBody @Valid CardDTO cardDTO,
														   @PathVariable(name = "id") Long requestId,
														   @RequestParam(value = "client_id") Long clientId) {
		captchaService.validateCaptcha(cardDTO.getCaptchaResponse());

		var request = requestService.findById(requestId);
		var client = clientService.findById(clientId);

		requestService.paySpecialist(request, client);

		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
