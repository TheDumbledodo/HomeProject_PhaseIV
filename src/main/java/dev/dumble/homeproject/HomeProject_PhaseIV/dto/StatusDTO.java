package dev.dumble.homeproject.HomeProject_PhaseIV.dto;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.enums.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatusDTO {

	private RequestStatus status;
}
