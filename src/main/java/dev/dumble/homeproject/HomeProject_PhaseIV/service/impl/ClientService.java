package dev.dumble.homeproject.HomeProject_PhaseIV.service.impl;

import dev.dumble.homeproject.HomeProject_PhaseIV.dto.ChangePasswordDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.ConfirmationToken;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.members.Client;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.enums.UserRole;
import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.DuplicateEntityException;
import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.NotPermittedException;
import dev.dumble.homeproject.HomeProject_PhaseIV.repository.IClientRepository;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.GenericService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService extends GenericService<Long, IClientRepository, Client> {

	private final BCryptPasswordEncoder passwordEncoder;
	private final EmailService emailService;

	public ClientService(IClientRepository repository, BCryptPasswordEncoder passwordEncoder, EmailService emailService) {
		super(repository);
		this.passwordEncoder = passwordEncoder;
		this.emailService = emailService;
	}

	@Override
	public Client create(Client client) {
		var emailAddress = client.getEmailAddress();

		if (super.getRepository().findClientByEmailAddress(emailAddress).isPresent())
			throw new DuplicateEntityException("The email you provided already exists.");

		var token = new ConfirmationToken();
		var mailMessage = new SimpleMailMessage();
		var request = "http://localhost:8081/api/v1/client/confirm-account?token=%s".formatted(token.getConfirmationToken());

		mailMessage.setTo(emailAddress);
		mailMessage.setFrom("Home_Project_IV");
		mailMessage.setSubject("Home Project IV's Email Verification Link!");
		mailMessage.setText("Confirm your account by clicking on this link: %s".formatted(request));

		emailService.sendMail(mailMessage);

		client.setPassword(passwordEncoder.encode(client.getPassword()));
		client.setRegisteredTime(LocalDateTime.now());
		client.setUserRole(UserRole.ROLE_CLIENT);
		client.setToken(token);

		return super.getRepository().save(client);
	}

	public Optional<Client> findByName(String username) {
		return super.getRepository().findClientByUsername(username);
	}

	public void changePassword(Client client, ChangePasswordDTO passwordDTO) {
		if (!passwordEncoder.matches(passwordDTO.getOldPassword(), client.getPassword()))
			throw new NotPermittedException("The password you entered doesn't match your current password.");

		client.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
		super.update(client);
	}

	public List<Client> findAll(Specification<Client> specification) {
		return super.getRepository().findAll(specification);
	}
}
