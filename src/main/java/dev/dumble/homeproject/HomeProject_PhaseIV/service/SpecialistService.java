package dev.dumble.homeproject.HomeProject_PhaseIV.service;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.members.Specialist;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.services.Assistance;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.enums.SpecialistStatus;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.enums.UserRole;
import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.*;
import dev.dumble.homeproject.HomeProject_PhaseIV.repository.ISpecialistRepository;
import dev.dumble.homeproject.HomeProject_PhaseIV.utils.Utility;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SpecialistService {

	private final ISpecialistRepository repository;

	public Specialist register(Specialist specialist) {

		if (!specialist.getPassword().matches(Utility.PASSWORD_PATTERN))
			throw new WeekPasswordException();

		if (specialist.getProfilePicture() == null)
			throw new ImproperProfilePictureException();

		var emailAddress = specialist.getEmailAddress();
		if (emailAddress == null)
			throw new MissingInformationException();

		if (repository.findClientByEmailAddress(emailAddress).isPresent())
			throw new DuplicateEntityException();

		specialist.setRegisteredTime(Utility.getPresentDate());
		specialist.setUserRole(UserRole.SPECIALIST);
		specialist.setStatus(SpecialistStatus.DISABLED);

		return repository.save(specialist);
	}

	public Specialist login(String username, String password) {

		if (!password.matches(Utility.PASSWORD_PATTERN))
			throw new WeekPasswordException();

		return repository
				.findSpecialistByUsernameAndPassword(username, password)
				.orElseThrow(InvalidEntityException::new);
	}

	public Specialist read(Specialist specialist) {
		return repository.findOne(Example.of(specialist)).orElseThrow(InvalidEntityException::new);
	}

	public List<Specialist> findAll(Specification<Specialist> specification) {
		return repository.findAll(specification);
	}

	public Specialist update(Specialist specialist) {
		var id = specialist.getId();
		if (id == null)
			throw new InvalidEntityException();

		if (repository.findById(id).isEmpty())
			throw new InvalidEntityException();

		return repository.save(specialist);
	}

	public void delete(Specialist specialist) {
		repository.delete(specialist);
	}

	public Specialist changePassword(Specialist specialist, String password) {
		var status = specialist.getStatus();
		Utility.checkSpecialistStatus(status == SpecialistStatus.ACCEPTED);

		if (!password.matches(Utility.PASSWORD_PATTERN))
			throw new WeekPasswordException();

		specialist.setPassword(password);

		return this.update(specialist);
	}

	public Specialist changeStatus(Specialist specialist, SpecialistStatus status) {
		specialist.setStatus(status);
		return this.update(specialist);
	}

	public Specialist addAssistance(Specialist specialist, Assistance assistance) {
		Utility.checkSpecialistStatus(specialist.getStatus() == SpecialistStatus.ACCEPTED);
		specialist.addAssistance(assistance);

		return this.update(specialist);
	}

	public Specialist removeAssistance(Specialist specialist, Assistance assistance) {
		Utility.checkSpecialistStatus(specialist.getStatus() == SpecialistStatus.ACCEPTED);
		specialist.removeAssistance(assistance);

		return this.update(specialist);
	}

	public Specialist findById(Long id) {
		return repository.findById(id).orElseThrow(InvalidEntityException::new);
	}
}
