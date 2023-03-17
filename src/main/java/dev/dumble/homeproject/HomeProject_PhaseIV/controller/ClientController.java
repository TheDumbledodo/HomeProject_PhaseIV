package dev.dumble.homeproject.HomeProject_PhaseIV.controller;

import dev.dumble.homeproject.HomeProject_PhaseIV.dto.ChangePasswordDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.dto.RequestDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.dto.StatusDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.dto.UserDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.users.Client;
import dev.dumble.homeproject.HomeProject_PhaseIV.mapper.ClientMapper;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.impl.ClientService;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.impl.ConfirmationTokenService;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.impl.RequestService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/client")
public class ClientController {

	private ClientService clientService;
	private RequestService requestService;
	private ConfirmationTokenService tokenService;

	@GetMapping("/register")
	public void registerClient(@RequestBody @Valid UserDTO userDTO) {
		var client = ClientMapper.getInstance().map(userDTO);

		clientService.create(client);
	}

	@PostMapping("/change-password")
	public void updateClientPassword(@RequestBody @Valid ChangePasswordDTO passwordDTO) {
		var client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		clientService.changePassword(client, passwordDTO);
	}

	@GetMapping("/confirm-account")
	public ResponseEntity<String> confirmClientAccount(@RequestParam(value = "token") @NonNull @NotBlank String token) {
		tokenService.confirmToken(token);

		return ResponseEntity.ok("Your account has been verified!");
	}

	@GetMapping("/all-requests")
	public ResponseEntity<Set<RequestDTO>> findClientRequests(@RequestBody @Valid StatusDTO status) {
		var client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		var requests = requestService.findClientRequests(client, status.getStatus());
		var optionalRequests = Optional.ofNullable(requests);

		return ResponseEntity.of(optionalRequests);
	}

	@GetMapping("/credit")
	public ResponseEntity<Long> findClientCredit() {
		var client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		var optionalRating = Optional.of(clientService.getCredit(client));

		return ResponseEntity.of(optionalRating);
	}
}
