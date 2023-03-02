package dev.dumble.homeproject.HomeProject_PhaseIV.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {

	private final JavaMailSender mailSender;

	public void sendMail(SimpleMailMessage mailMessage) {
		mailSender.send(mailMessage);
	}
}
