package dev.dumble.homeproject.HomeProject_PhaseIV.config;

import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.InvalidEntityException;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.impl.ClientService;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.impl.ManagerService;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.impl.SpecialistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	private final BCryptPasswordEncoder passwordEncoder;
	private final SpecialistService specialistService;
	private final ManagerService managerService;
	private final ClientService clientService;

	public SecurityConfig(BCryptPasswordEncoder passwordEncoder, SpecialistService specialistService,
						  ManagerService managerService, ClientService clientService) {
		this.passwordEncoder = passwordEncoder;
		this.specialistService = specialistService;
		this.managerService = managerService;
		this.clientService = clientService;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.csrf().disable()
				.authorizeHttpRequests()
				.requestMatchers("/api/v1/specialist/register").permitAll()
				.requestMatchers("/api/v1/client/register").permitAll()
				.requestMatchers("/api/v1/manager/create").permitAll()

				.requestMatchers("/api/v1/specialist/**").hasRole("SPECIALIST")

				.requestMatchers("/api/v1/client/**").hasRole("CLIENT")
				.requestMatchers("/api/v1/request/**").hasRole("CLIENT")

				.requestMatchers("/api/v1/manager/**").hasRole("MANAGER")
				.requestMatchers("/api/v1/assistance/**", "/api/v1/assistance-group/**").hasRole("MANAGER")
				.anyRequest().authenticated()
				.and().httpBasic();

		return http.build();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(
						(username) -> {
							var specialist = specialistService.findByName(username);
							var manager = managerService.findByName(username);
							var client = clientService.findByName(username);

							var users = Arrays.asList(specialist, manager, client);

							for (var optionalUser : users) {
								if (optionalUser.isEmpty()) continue;

								return optionalUser.get();
							}
							throw new InvalidEntityException("These entered login credentials are wrong!");
						})
				.passwordEncoder(passwordEncoder);
	}
}
