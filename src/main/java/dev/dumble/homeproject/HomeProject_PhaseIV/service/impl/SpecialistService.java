package dev.dumble.homeproject.HomeProject_PhaseIV.service.impl;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.members.Specialist;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.services.Assistance;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.enums.SpecialistStatus;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.enums.UserRole;
import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.DuplicateEntityException;
import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.InvalidEntityException;
import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.NotPermittedException;
import dev.dumble.homeproject.HomeProject_PhaseIV.repository.ISpecialistRepository;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.GenericService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SpecialistService extends GenericService<Long, ISpecialistRepository, Specialist> {

	public SpecialistService(ISpecialistRepository repository) {
		super(repository);
	}

	@Override
	public Specialist create(Specialist specialist) {
		if (super.getRepository()
				.findClientByEmailAddress(specialist.getEmailAddress())
				.isPresent())
			throw new DuplicateEntityException("The email you provided already exists.");

		specialist.setRegisteredTime(LocalDateTime.now());
		specialist.setUserRole(UserRole.SPECIALIST);
		specialist.setStatus(SpecialistStatus.DISABLED);

		return super.getRepository().save(specialist);
	}

	public Specialist login(String username, String password) {
		return super.getRepository()
				.findSpecialistByUsernameAndPassword(username, password)
				.orElseThrow(() -> new InvalidEntityException("These entered login credentials are wrong!"));
	}

	public void changePassword(Specialist specialist, String password) {
		if (specialist.isDisabled())
			throw new NotPermittedException("The specialist hasn't been accepted yet.");

		specialist.setPassword(password);
		this.update(specialist);
	}

	public void addAssistance(Specialist specialist, Assistance assistance) {
		if (specialist.isDisabled())
			throw new NotPermittedException("The specialist hasn't been accepted yet.");

		if (specialist.containsAssistance(assistance))
			throw new DuplicateEntityException("The specialist already has that assistance.");

		specialist.addAssistance(assistance);
		super.update(specialist);
	}

	public void removeAssistance(Specialist specialist, Assistance assistance) {
		if (specialist.isDisabled())
			throw new NotPermittedException("The specialist hasn't been accepted yet.");

		if (!specialist.containsAssistance(assistance))
			throw new InvalidEntityException("The specialists assistances doesn't include that assistance.");

		specialist.removeAssistance(assistance);
		super.update(specialist);
	}

	public void changeStatus(Specialist specialist, SpecialistStatus status) {
		specialist.setStatus(status);
		super.update(specialist);
	}

	public List<Specialist> findAll(Specification<Specialist> specification) {
		return super.getRepository().findAll(specification);
	}
}
