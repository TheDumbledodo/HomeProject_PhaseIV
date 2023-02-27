package dev.dumble.homeproject.HomeProject_PhaseIV.service;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.members.Client;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.enums.UserRole;
import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.DuplicateEntityException;
import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.InvalidEntityException;
import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.MissingInformationException;
import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.WeekPasswordException;
import dev.dumble.homeproject.HomeProject_PhaseIV.repository.IClientRepository;
import dev.dumble.homeproject.HomeProject_PhaseIV.utils.Utility;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClientService {

	private final IClientRepository repository;

	public Client register(Client client) {

		if (!client.getPassword().matches(Utility.PASSWORD_PATTERN))
			throw new WeekPasswordException();

		var emailAddress = client.getEmailAddress();
		if (emailAddress == null)
			throw new MissingInformationException();

		if (repository.findClientByEmailAddress(emailAddress).isPresent())
			throw new DuplicateEntityException();

		client.setRegisteredTime(Utility.getPresentDate());
		client.setUserRole(UserRole.CLIENT);

		return repository.save(client);
	}

	public Client login(String username, String password) {
		return repository
				.findClientByUsernameAndPassword(username, password)
				.orElseThrow(InvalidEntityException::new);
	}

	public Client read(Client client) {
		return repository.findOne(Example.of(client)).orElseThrow(InvalidEntityException::new);
	}

	public List<Client> findAll(Specification<Client> specification) {
		return repository.findAll();
	}

	public Client update(Client client) {
		var id = client.getId();
		if (id == null)
			throw new InvalidEntityException();

		if (repository.findById(id).isEmpty())
			throw new InvalidEntityException();

		return repository.save(client);
	}

	public void delete(Client client) {
		repository.delete(client);
	}

	public Client changePassword(Client client, String password) {
		if (!password.matches(Utility.PASSWORD_PATTERN))
			throw new WeekPasswordException();

		client.setPassword(password);

		return this.update(client);
	}

	public Client findById(Long id) {
		return repository.findById(id).orElseThrow(InvalidEntityException::new);
	}
}
