package dev.dumble.homeproject.HomeProject_PhaseIV.service.impl;

import dev.dumble.homeproject.HomeProject_PhaseIV.dto.ChangePasswordDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.dto.UserDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.Assistance;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.ConfirmationToken;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.users.Specialist;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.enums.SpecialistStatus;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.enums.UserRole;
import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.DuplicateEntityException;
import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.InvalidEntityException;
import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.NotPermittedException;
import dev.dumble.homeproject.HomeProject_PhaseIV.filter.SearchSpecification;
import dev.dumble.homeproject.HomeProject_PhaseIV.filter.request.SearchRequest;
import dev.dumble.homeproject.HomeProject_PhaseIV.mapper.SpecialistMapper;
import dev.dumble.homeproject.HomeProject_PhaseIV.repository.ISpecialistRepository;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.GenericService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SpecialistService extends GenericService<Long, ISpecialistRepository, Specialist> {

	private final BCryptPasswordEncoder passwordEncoder;
	private final EmailService emailService;

	public SpecialistService(ISpecialistRepository repository, BCryptPasswordEncoder passwordEncoder, EmailService emailService) {
		super(repository);
		this.passwordEncoder = passwordEncoder;
		this.emailService = emailService;
	}

	@Override
	public Specialist create(Specialist specialist) {
		var emailAddress = specialist.getEmailAddress();

		if (super.getRepository()
				.findSpecialistByEmailAddress(emailAddress)
				.isPresent())
			throw new DuplicateEntityException("The email you provided already exists.");

		var token = new ConfirmationToken();
		var mailMessage = new SimpleMailMessage();
		var request = "http://localhost:8081/api/v1/specialist/confirm-account?token=%s".formatted(token.getConfirmationToken());

		mailMessage.setTo(emailAddress);
		mailMessage.setFrom("Home_Project_IV");
		mailMessage.setSubject("Home Project IV's Email Verification Link!");
		mailMessage.setText("Confirm your account by clicking on this link: %s".formatted(request));

		emailService.sendMail(mailMessage);

		specialist.setPassword(passwordEncoder.encode(specialist.getPassword()));
		specialist.setRegisteredTime(LocalDateTime.now());
		specialist.setUserRole(UserRole.ROLE_SPECIALIST);
		specialist.setStatus(SpecialistStatus.RECENT);
		specialist.setToken(token);

		return super.getRepository().save(specialist);
	}

	public Optional<Specialist> findByName(String username) {
		return super.getRepository().findSpecialistByUsername(username);
	}

	public Optional<Specialist> findByToken(Long tokenId) {
		return super.getRepository().findSpecialistByConfirmationToken(tokenId);
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

	public void acceptSpecialist(Specialist specialist) {
		specialist.setStatus(SpecialistStatus.ACCEPTED);
		specialist.getToken().setUsed(true);
		specialist.setVerified(true);
		super.update(specialist);
	}

	public Set<UserDTO> findAll(SearchRequest request) {
		var specification = new SearchSpecification<Specialist>(request);
		var pageable = SearchSpecification.getPageable(request.getSize());

		var content = super.getRepository().findAll(specification, pageable).getContent();
		return content.stream()
				.map(specialist -> SpecialistMapper.getInstance().serialize(specialist))
				.collect(Collectors.toSet());
	}

	public Long getRating(Specialist specialist) {
		if (specialist.isNotAccepted())
			throw new NotPermittedException("The specialist hasn't been accepted yet.");

		return specialist.getRating();
	}
}
