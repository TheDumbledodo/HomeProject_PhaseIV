package dev.dumble.homeproject.HomeProject_PhaseIV.mapper;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.transactions.Request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTO {

	private LocalDateTime dueTime;

	private String description;
	private String address;

	private Long offeredPrice;
	private Long assistanceId;
	private Long clientId;

	public Request toRequest() {
		var requestDueTime = this.getDueTime();
		var requestDescription = this.getDescription();
		var requestAddress = this.getAddress();
		var requestOfferedPrice = this.getOfferedPrice();

		return Request.builder()
				.setDueTime(requestDueTime)
				.setDescription(requestDescription)
				.setAddress(requestAddress)
				.setOfferedPrice(requestOfferedPrice)
				.build();
	}
}
