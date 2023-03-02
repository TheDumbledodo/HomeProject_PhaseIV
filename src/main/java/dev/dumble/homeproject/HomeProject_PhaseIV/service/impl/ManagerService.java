package dev.dumble.homeproject.HomeProject_PhaseIV.service.impl;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.users.Manager;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.enums.UserRole;
import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.DuplicateEntityException;
import dev.dumble.homeproject.HomeProject_PhaseIV.repository.IManagerRepository;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.GenericService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ManagerService extends GenericService<Long, IManagerRepository, Manager> {

	private final BCryptPasswordEncoder passwordEncoder;

	public ManagerService(IManagerRepository repository, BCryptPasswordEncoder passwordEncoder) {
		super(repository);
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Manager create(Manager manager) {
		var emailAddress = manager.getEmailAddress();

		if (super.getRepository().findManagerByEmailAddress(emailAddress).isPresent())
			throw new DuplicateEntityException("The email you provided already exists.");

		manager.setPassword(passwordEncoder.encode(manager.getPassword()));
		manager.setRegisteredTime(LocalDateTime.now());
		manager.setUserRole(UserRole.ROLE_MANAGER);

		return super.getRepository().save(manager);
	}

	public Optional<Manager> findByName(String username) {
		return super.getRepository().findManagerByUsername(username);
	}
}
