package dev.dumble.homeproject.HomeProject_PhaseIV.service.impl;

import dev.dumble.homeproject.HomeProject_PhaseIV.dto.ChangePasswordDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.dto.UserDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.ConfirmationToken;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.users.Client;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.enums.UserRole;
import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.DuplicateEntityException;
import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.NotPermittedException;
import dev.dumble.homeproject.HomeProject_PhaseIV.filter.SearchSpecification;
import dev.dumble.homeproject.HomeProject_PhaseIV.filter.request.SearchRequest;
import dev.dumble.homeproject.HomeProject_PhaseIV.mapper.ClientMapper;
import dev.dumble.homeproject.HomeProject_PhaseIV.repository.IClientRepository;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.GenericService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

	public Optional<Client> findByToken(Long tokenId) {
		return super.getRepository().findClientByConfirmationToken(tokenId);
	}

	public void changePassword(Client client, ChangePasswordDTO passwordDTO) {
		if (!client.isVerified())
			throw new NotPermittedException("The clients account hasn't been verified yet.");

		if (!passwordEncoder.matches(passwordDTO.getOldPassword(), client.getPassword()))
			throw new NotPermittedException("The password you entered doesn't match your current password.");

		client.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
		super.update(client);
	}

	public Long getCredit(Client client) {
		if (!client.isVerified())
			throw new NotPermittedException("The clients account hasn't been verified yet.");

		return client.getCredit();
	}

	public Set<UserDTO> findAll(SearchRequest request) {
		var specification = new SearchSpecification<Client>(request);
		var pageable = SearchSpecification.getPageable(request.getSize());

		var content = super.getRepository().findAll(specification, pageable).getContent();
		return content.stream()
				.map(client -> ClientMapper.getInstance().serialize(client))
				.collect(Collectors.toSet());
	}
}
