package dev.dumble.homeproject.HomeProject_PhaseIV.controller;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.members.Client;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.transactions.Request;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.enums.RequestStatus;
import dev.dumble.homeproject.HomeProject_PhaseIV.mapper.ClientDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.mapper.LoginDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.ClientService;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.RequestService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/client")
public class ClientController {

	private ClientService clientService;
	private RequestService requestService;

	@PostMapping("/register")
	public ResponseEntity<Client> registerClient(@RequestBody ClientDTO clientDTO) {
		var client = clientDTO.toClient();
		var optionalClient = Optional.ofNullable(clientService.register(client));

		return ResponseEntity.of(optionalClient);
	}

	@PostMapping("/login")
	public ResponseEntity<Client> loginClient(@RequestBody LoginDTO loginDTO) {
		var client = clientService.login(loginDTO.getUsername(), loginDTO.getPassword());
		var optionalClient = Optional.ofNullable(client);

		return ResponseEntity.of(optionalClient);
	}

	@PostMapping("/change-password")
	public ResponseEntity<Client> updateClientPassword(@RequestParam(value = "new_password") String newPassword,
													   @RequestBody LoginDTO loginDTO) {
		var client = clientService.login(loginDTO.getUsername(), loginDTO.getPassword());

		client.setPassword(newPassword);
		var updatedAssistance = clientService.update(client);
		var optionalClient = Optional.ofNullable(updatedAssistance);

		return ResponseEntity.of(optionalClient);
	}

	@GetMapping("/find-requests")
	public ResponseEntity<Set<Request>> findClientRequests(@RequestParam(value = "client_id") Long clientId,
														   @RequestParam(value = "request_status") RequestStatus status) {
		var client = clientService.findById(clientId);

		var requests = requestService.findClientRequests(client, status);
		var optionalRequests = Optional.ofNullable(requests);

		return ResponseEntity.of(optionalRequests);
	}
}
