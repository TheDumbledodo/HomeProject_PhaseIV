package dev.dumble.homeproject.HomeProject_PhaseIV.service.impl;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.members.Specialist;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.transactions.Review;
import dev.dumble.homeproject.HomeProject_PhaseIV.repository.IReviewRepository;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.GenericService;
import org.springframework.stereotype.Service;

@Service
public class ReviewService extends GenericService<Long, IReviewRepository, Review> {

	private final SpecialistService specialistService;

	public ReviewService(IReviewRepository repository, SpecialistService specialistService) {
		super(repository);
		this.specialistService = specialistService;
	}

	public void addReviewSpecialist(Specialist specialist, String description, int rating) {
		var review = Review.builder()
				.setRating(rating)
				.setSpecialist(specialist)
				.setDescription(description)
				.build();

		super.create(review);
		specialist.addRating(rating);

		specialistService.update(specialist);
	}
}
