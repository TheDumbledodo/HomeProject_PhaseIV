package dev.dumble.homeproject.HomeProject_PhaseIV.service.impl;

import dev.dumble.homeproject.HomeProject_PhaseIV.dto.ChangePasswordDTO;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SpecialistService extends GenericService<Long, ISpecialistRepository, Specialist> {

	private final BCryptPasswordEncoder passwordEncoder;

	public SpecialistService(ISpecialistRepository repository, BCryptPasswordEncoder passwordEncoder) {
		super(repository);
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Specialist create(Specialist specialist) {
		if (super.getRepository()
				.findClientByEmailAddress(specialist.getEmailAddress())
				.isPresent())
			throw new DuplicateEntityException("The email you provided already exists.");

		specialist.setPassword(passwordEncoder.encode(specialist.getPassword()));
		specialist.setRegisteredTime(LocalDateTime.now());
		specialist.setUserRole(UserRole.ROLE_SPECIALIST);
		specialist.setStatus(SpecialistStatus.RECENT);

		return super.getRepository().save(specialist);
	}

	public Optional<Specialist> findByName(String username) {
		return super.getRepository().findSpecialistByUsername(username);
	}

	public void changePassword(Specialist specialist, ChangePasswordDTO passwordDTO) {
		if (specialist.isNotAccepted())
			throw new NotPermittedException("The specialist hasn't been accepted yet.");

		if (!passwordEncoder.matches(passwordDTO.getOldPassword(), specialist.getPassword()))
			throw new NotPermittedException("The password you entered doesn't match your current password.");

		specialist.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
		super.update(specialist);
	}

	public void addAssistance(Specialist specialist, Assistance assistance) {
		if (specialist.isNotAccepted())
			throw new NotPermittedException("The specialist hasn't been accepted yet.");

		if (specialist.containsAssistance(assistance))
			throw new DuplicateEntityException("The specialist already has that assistance.");

		specialist.addAssistance(assistance);
		super.update(specialist);
	}

	public void removeAssistance(Specialist specialist, Assistance assistance) {
		if (specialist.isNotAccepted())
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
