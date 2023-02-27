package dev.dumble.homeproject.HomeProject_PhaseIV.controller;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.transactions.Request;
import dev.dumble.homeproject.HomeProject_PhaseIV.mapper.CardDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.mapper.RequestDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.mapper.ReviewDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/request")
public class RequestController {

	private RequestService requestService;
	private OfferService offerService;
	private AssistanceService assistanceService;
	private CaptchaService captchaService;
	private ClientService clientService;

	@PostMapping("/create")
	public ResponseEntity<Request> createRequest(@RequestBody RequestDTO requestDTO) {
		var request = requestDTO.toRequest();
		var assistance = assistanceService.findById(requestDTO.getAssistanceId());
		var client = clientService.findById(requestDTO.getClientId());

		var optionalRequest = Optional.ofNullable(requestService.create(request, assistance, client));

		return ResponseEntity.of(optionalRequest);
	}

	@PostMapping("/accept-offer")
	public ResponseEntity<Request> acceptRequestOffer(@RequestParam(value = "offer_id") Long offerId,
													  @RequestParam(value = "request_id") Long requestId) {
		var databaseOffer = offerService.findById(offerId);
		var databaseRequest = requestService.findById(requestId);

		var request = offerService.acceptOffer(databaseRequest, databaseOffer);
		var optionalRequest = Optional.of(request);

		return ResponseEntity.of(optionalRequest);
	}

	@PostMapping("/start-request")
	public ResponseEntity<Request> startRequest(@RequestParam(value = "offer_id") Long offerId,
												@RequestParam(value = "request_id") Long requestId) {
		var databaseOffer = offerService.findById(offerId);
		var databaseRequest = requestService.findById(requestId);

		var request = requestService.confirmRequestStarted(databaseRequest, databaseOffer);
		var optionalRequest = Optional.of(request);

		return ResponseEntity.of(optionalRequest);
	}

	@PostMapping("/finish-request")
	public ResponseEntity<Request> finishRequest(@RequestParam(value = "request_id") Long requestId) {
		var request = requestService.findById(requestId);

		var databaseRequest = requestService.finishRequest(request);
		var optionalRequest = Optional.of(databaseRequest);

		return ResponseEntity.of(optionalRequest);
	}

	@PostMapping("/credit-payment")
	public ResponseEntity<Request> creditPaymentForRequest(@RequestBody ReviewDTO reviewDTO,
														   @RequestParam(value = "request_id") Long requestId,
														   @RequestParam(value = "client_id") Long clientId) {
		var request = requestService.findById(requestId);
		var client = clientService.findById(clientId);
		var review = reviewDTO.toReview();

		var databaseRequest = requestService.payByCredit(request, client, review);
		var optionalRequest = Optional.of(databaseRequest);

		return ResponseEntity.of(optionalRequest);
	}

	@PostMapping("/online-payment")
	public ResponseEntity<Request> onlinePaymentForRequest(@RequestBody final CardDTO cardDTO,
														   @RequestParam(value = "request_id") Long requestId,
														   @RequestParam(value = "client_id") Long clientId) {
		captchaService.validateCaptcha(cardDTO.getCaptchaResponse());

		var request = requestService.findById(requestId);
		var client = clientService.findById(clientId);

		var databaseRequest = requestService.payOnline(request, client);
		var optionalRequest = Optional.of(databaseRequest);

		return ResponseEntity.of(optionalRequest);
	}
}
