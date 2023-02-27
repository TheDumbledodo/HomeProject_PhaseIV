package dev.dumble.homeproject.HomeProject_PhaseIV.service.impl;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.members.Client;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.enums.UserRole;
import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.DuplicateEntityException;
import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.InvalidEntityException;
import dev.dumble.homeproject.HomeProject_PhaseIV.repository.IClientRepository;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.GenericService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClientService extends GenericService<Long, IClientRepository, Client> {

	public ClientService(IClientRepository repository) {
		super(repository);
	}

	@Override
	public Client create(Client client) {
		var emailAddress = client.getEmailAddress();

		if (super.getRepository().findClientByEmailAddress(emailAddress).isPresent())
			throw new DuplicateEntityException("The email you provided already exists.");

		client.setRegisteredTime(LocalDateTime.now());
		client.setUserRole(UserRole.CLIENT);

		return super.getRepository().save(client);
	}

	public Client login(String username, String password) {
		return super.getRepository()
				.findClientByUsernameAndPassword(username, password)
				.orElseThrow(() -> new InvalidEntityException("These login credentials are wrong!"));
	}

	public void changePassword(Client client, String password) {
		client.setPassword(password);
		this.update(client);
	}

	public List<Client> findAll(Specification<Client> specification) {
		return super.getRepository().findAll(specification);
	}
}
