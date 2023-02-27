package dev.dumble.homeproject.HomeProject_PhaseIV.service;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.members.Manager;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.enums.UserRole;
import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.DuplicateEntityException;
import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.InvalidEntityException;
import dev.dumble.homeproject.HomeProject_PhaseIV.repository.IManagerRepository;
import dev.dumble.homeproject.HomeProject_PhaseIV.utils.Utility;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ManagerService {

	private final IManagerRepository repository;

	public Manager create(Manager manager) {
		if (this.exists(manager))
			throw new DuplicateEntityException();

		manager.setRegisteredTime(Utility.getPresentDate());
		manager.setUserRole(UserRole.MANAGER);

		return repository.save(manager);
	}

	public Manager read(Manager manager) {
		return repository.findOne(Example.of(manager)).orElseThrow(InvalidEntityException::new);
	}

	public Manager update(Manager manager) {
		var id = manager.getId();
		if (id == null)
			throw new InvalidEntityException();

		if (repository.findById(id).isEmpty())
			throw new InvalidEntityException();

		return repository.save(manager);
	}

	public void delete(Manager manager) {
		repository.delete(manager);
	}

	public boolean exists(Manager manager) {
		return repository.exists(Example.of(manager));
	}

	public Manager findById(Long id) {
		return repository.findById(id).orElseThrow(InvalidEntityException::new);
	}
}
