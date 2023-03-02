package dev.dumble.homeproject.HomeProject_PhaseIV.controller;

import dev.dumble.homeproject.HomeProject_PhaseIV.dto.ChangePasswordDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.dto.UserDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.members.Client;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.transactions.Request;
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
	public ResponseEntity<Client> confirmClientAccount(@RequestParam(value = "token") @NonNull @NotBlank String token) {
		var client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		tokenService.confirmToken(client, token);

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	// todo: clean this code change to enum or specification
	@GetMapping("/find-requests")
	public ResponseEntity<Set<Request>> findClientRequests(@RequestParam(value = "request_status") @NonNull @NotBlank String status) {
		var client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		var requests = requestService.findClientRequests(client, status);
		var optionalRequests = Optional.ofNullable(requests);

		return ResponseEntity.of(optionalRequests);
	}
}
