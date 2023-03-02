package dev.dumble.homeproject.HomeProject_PhaseIV.controller;

import dev.dumble.homeproject.HomeProject_PhaseIV.dto.ChangePasswordDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.dto.StatusDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.dto.UserDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.transactions.Request;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.users.Client;
import dev.dumble.homeproject.HomeProject_PhaseIV.mappers.ClientMapper;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.impl.ClientService;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.impl.ConfirmationTokenService;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.impl.RequestService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@Slf4j @RestController
@AllArgsConstructor
@RequestMapping("/api/v1/client")
public class ClientController {

	private ClientService clientService;
	private RequestService requestService;
	private ConfirmationTokenService tokenService;

	@PostMapping("/register")
	public ResponseEntity<Client> registerClient(@RequestBody @Valid UserDTO userDTO) {
		var client = ClientMapper.getInstance().map(userDTO);

		clientService.create(client);

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PostMapping("/change-password")
	public ResponseEntity<Client> updateClientPassword(@RequestBody @Valid ChangePasswordDTO passwordDTO) {
		var client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		clientService.changePassword(client, passwordDTO);

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PostMapping("/confirm-account")
	public ResponseEntity<String> confirmClientAccount(@RequestParam(value = "token") @NonNull @NotBlank String token) {
		var client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		tokenService.confirmToken(client, token);

		return ResponseEntity.ok("Your account has been verified!");
	}

	@GetMapping("/all-requests")
	public ResponseEntity<Set<Request>> findClientRequests(@RequestBody @Valid StatusDTO status) {
		var client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		var requests = requestService.findClientRequests(client, status.getStatus());
		var optionalRequests = Optional.ofNullable(requests);

		return ResponseEntity.of(optionalRequests);
	}

	@GetMapping("/credit")
	public ResponseEntity<Long> findClientCredit() {
		var client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		var optionalRating = Optional.of(client.getCredit());

		return ResponseEntity.of(optionalRating);
	}
}
