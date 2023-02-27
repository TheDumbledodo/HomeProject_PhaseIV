package dev.dumble.homeproject.HomeProject_PhaseIV.mapper;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.services.AssistanceGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssistanceGroupDTO {

	private String name;

	public AssistanceGroup toAssistanceGroup() {
		var groupName = this.getName();

		return AssistanceGroup.builder()
				.setName(groupName)
				.build();
	}
}
