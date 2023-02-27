package dev.dumble.homeproject.HomeProject_PhaseIV.mapper;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.members.Client;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {

	private String emailAddress;
	private String password;
	private String username;
	private String firstName;
	private String lastName;

	private Long credit;

	public Client toClient() {
		var clientFirstName = this.getFirstName();
		var clientLastName = this.getLastName();
		var clientUsername = this.getUsername();
		var clientPassword = this.getPassword();
		var clientCredit = this.getCredit();
		var clientEmailAddress = this.getEmailAddress();

		return Client.builder()
				.setFirstName(clientFirstName)
				.setLastName(clientLastName)
				.setUsername(clientUsername)
				.setPassword(clientPassword)
				.setCredit(clientCredit)
				.setEmailAddress(clientEmailAddress)
				.build();
	}
}
