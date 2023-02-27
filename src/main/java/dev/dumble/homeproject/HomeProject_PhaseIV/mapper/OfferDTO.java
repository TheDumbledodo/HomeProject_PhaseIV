package dev.dumble.homeproject.HomeProject_PhaseIV.mapper;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.transactions.Offer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class OfferDTO {

	private LocalDateTime creationTime;
	private LocalDateTime startTime;

	private String description;
	private Integer practicalDays;

	private Long offeredPrice;
	private Long specialistId;
	private Long requestId;

	public Offer toOffer() {
		var offerCreationTime = this.getCreationTime();
		var offerStartTime = this.getStartTime();
		var offerDescription = this.getDescription();
		var offerPracticalDays = this.getPracticalDays();
		var offerOfferedPrice = this.getOfferedPrice();

		return Offer.builder()
				.setCreationTime(offerCreationTime)
				.setStartTime(offerStartTime)
				.setDescription(offerDescription)
				.setPracticalDays(offerPracticalDays)
				.setOfferedPrice(offerOfferedPrice)
				.build();
	}
}
