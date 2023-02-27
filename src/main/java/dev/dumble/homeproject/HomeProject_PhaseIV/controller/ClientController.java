package dev.dumble.homeproject.HomeProject_PhaseIV.controller;

import dev.dumble.homeproject.HomeProject_PhaseIV.dto.LoginDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.dto.UserDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.members.Client;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.transactions.Request;
import dev.dumble.homeproject.HomeProject_PhaseIV.mappers.ClientMapper;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.impl.ClientService;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.impl.RequestService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@Slf4j @RestController
@AllArgsConstructor
@RequestMapping("/api/v1/client")
public class ClientController {

	private ClientService clientService;
	private RequestService requestService;

	@PostMapping("/register")
	public ResponseEntity<Client> registerClient(@RequestBody @Valid UserDTO userDTO) {
		var client = ClientMapper.getInstance().map(userDTO);
		clientService.create(client);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping("/login")
	public ResponseEntity<Client> loginClient(@RequestBody @Valid LoginDTO loginDTO) {
		clientService.login(loginDTO.getUsername(), loginDTO.getPassword());

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PostMapping("/change-password")
	public ResponseEntity<Client> updateClientPassword(
			@RequestParam(value = "new_password") @NonNull @NotBlank
			@Pattern(
					regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@$!%*?&#^])[A-Za-z0-9@$!%*?&]{8,10}$",
					message = """
							The password must contain at least 8 to 10 characters containing an uppercase,
							a lowercase, one number and one of these @$!%*?&] characters.
							""")
			String recentPassword,
			@RequestBody @Valid LoginDTO loginDTO) {

		var client = clientService.login(loginDTO.getUsername(), loginDTO.getPassword());
		clientService.changePassword(client, recentPassword);

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	// todo: clean this code change to enum or specification
	@GetMapping("/{id}/find-requests")
	public ResponseEntity<Set<Request>> findClientRequests(@PathVariable(name = "id") Long clientId,
														   @RequestParam(value = "request_status") @NonNull @NotBlank String status) {
		var client = clientService.findById(clientId);
		var requests = requestService.findClientRequests(client, status);
		var optionalRequests = Optional.ofNullable(requests);

		return ResponseEntity.of(optionalRequests);
	}
}
