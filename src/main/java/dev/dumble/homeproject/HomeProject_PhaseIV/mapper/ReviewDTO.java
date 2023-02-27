package dev.dumble.homeproject.HomeProject_PhaseIV.mapper;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.transactions.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {

	private String description;
	private Integer rating;

	public Review toReview() {
		var reviewDescription = this.getDescription();
		var reviewRating = this.getRating();

		return Review.builder()
				.setDescription(reviewDescription)
				.setRating(reviewRating)
				.build();
	}
}
