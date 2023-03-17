package dev.dumble.homeproject.HomeProject_PhaseIV.service.impl;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.ConfirmationToken;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.enums.SpecialistStatus;
import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.InvalidEntityException;
import dev.dumble.homeproject.HomeProject_PhaseIV.repository.IConfirmationTokenRepository;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.GenericService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConfirmationTokenService extends GenericService<Long, IConfirmationTokenRepository, ConfirmationToken> {

	private final ClientService clientService;
	private final SpecialistService specialistService;

	public ConfirmationTokenService(IConfirmationTokenRepository repository, ClientService clientService, SpecialistService specialistService) {
		super(repository);
		this.clientService = clientService;
		this.specialistService = specialistService;
	}

	public void confirmToken(String tokenId) {
		var optionalToken = this.findTokenById(tokenId);
		if (optionalToken.isEmpty() || optionalToken.get().isUsed())
			throw new InvalidEntityException("The token you clicked on is already used or doesn't exist.");

		var token = optionalToken.get();
		var id = token.getId();
		token.setUsed(true);

		var optionalSpecialist = specialistService.findByToken(id);
		if (optionalSpecialist.isPresent()) {
			var specialist = optionalSpecialist.get();
			specialist.setVerified(true);
			specialist.setToken(token);

			specialist.setStatus(SpecialistStatus.PENDING_CONFIRMATION);
			specialistService.update(specialist);
			return;
		}

		var optionalClient = clientService.findByToken(id);
		if (optionalClient.isPresent()) {
			var client = optionalClient.get();

			client.setVerified(true);
			client.setToken(token);

			clientService.update(client);
		}
	}

	public Optional<ConfirmationToken> findTokenById(String confirmationToken) {
		return super.getRepository().findByConfirmationToken(confirmationToken);
	}
}
