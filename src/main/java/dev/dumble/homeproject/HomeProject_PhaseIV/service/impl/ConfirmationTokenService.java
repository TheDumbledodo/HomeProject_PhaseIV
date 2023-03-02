package dev.dumble.homeproject.HomeProject_PhaseIV.service.impl;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.UserEntity;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.ConfirmationToken;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.members.Client;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.members.Specialist;
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

	public void confirmToken(UserEntity userEntity, String tokenId) {
		var optionalToken = this.findByConfirmationToken(tokenId);
		if (optionalToken.isEmpty() || optionalToken.get().isUsed())
			throw new InvalidEntityException("The token you clicked on is already used.");

		var token = optionalToken.get();
		token.setUsed(true);

		userEntity.setEnabled(true);
		userEntity.setToken(token);

		if (userEntity instanceof Specialist specialist) {
			specialist.setStatus(SpecialistStatus.PENDING_CONFIRMATION);
			specialistService.update(specialist);
			return;
		}
		clientService.update((Client) userEntity);
	}

	public Optional<ConfirmationToken> findByConfirmationToken(String confirmationToken) {
		return super.getRepository().findByConfirmationToken(confirmationToken);
	}
}
