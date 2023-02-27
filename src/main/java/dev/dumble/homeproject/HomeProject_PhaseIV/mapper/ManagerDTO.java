package dev.dumble.homeproject.HomeProject_PhaseIV.mapper;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.members.Manager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ManagerDTO {

	private String emailAddress;
	private String password;
	private String username;

	public Manager toManager() {
		var clientUsername = this.getUsername();
		var clientPassword = this.getPassword();
		var clientEmailAddress = this.getEmailAddress();

		return Manager.builder()
				.setUsername(clientUsername)
				.setPassword(clientPassword)
				.setEmailAddress(clientEmailAddress)
				.build();
	}
}
