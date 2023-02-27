package dev.dumble.homeproject.HomeProject_PhaseIV.service.impl;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.members.Manager;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.enums.UserRole;
import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.DuplicateEntityException;
import dev.dumble.homeproject.HomeProject_PhaseIV.repository.IManagerRepository;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.GenericService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ManagerService extends GenericService<Long, IManagerRepository, Manager> {

	public ManagerService(IManagerRepository repository) {
		super(repository);
	}

	@Override
	public Manager create(Manager manager) {
		if (super.exists(manager))
			throw new DuplicateEntityException("The email you provided already exists.");

		manager.setRegisteredTime(LocalDateTime.now());
		manager.setUserRole(UserRole.MANAGER);

		return super.getRepository().save(manager);
	}
}
