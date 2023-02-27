package dev.dumble.homeproject.HomeProject_PhaseIV.mapper;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.services.Assistance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssistanceDTO {

	private String name;
	private String description;
	private Long minimumPrice;
	private AssistanceGroupDTO groupDTO;

	public Assistance toAssistance() {
		var assistanceName = this.getName();
		var assistanceDescription = this.getDescription();
		var assistanceMinimumPrice = this.getMinimumPrice();
		var assistanceGroupDTO = this.getGroupDTO();

		return Assistance.builder()
				.setName(assistanceName)
				.setDescription(assistanceDescription)
				.setMinimumPrice(assistanceMinimumPrice)
				.setGroup(assistanceGroupDTO.toAssistanceGroup())
				.build();
	}
}
