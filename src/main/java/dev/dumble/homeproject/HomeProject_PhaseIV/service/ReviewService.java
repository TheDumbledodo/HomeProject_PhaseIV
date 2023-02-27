package dev.dumble.homeproject.HomeProject_PhaseIV.service;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.members.Specialist;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.transactions.Review;
import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.DuplicateEntityException;
import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.InvalidEntityException;
import dev.dumble.homeproject.HomeProject_PhaseIV.repository.IReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReviewService {

	private final SpecialistService specialistService;

	private final IReviewRepository repository;

	public Review create(Review review) {
		if (this.exists(review))
			throw new DuplicateEntityException();

		return repository.save(review);
	}

	public Review read(Review review) {
		return repository.findOne(Example.of(review)).orElseThrow(InvalidEntityException::new);
	}

	public Review update(Review review) {
		var id = review.getId();
		if (id == null)
			throw new InvalidEntityException();

		if (repository.findById(id).isEmpty())
			throw new InvalidEntityException();

		return repository.save(review);
	}

	public void delete(Review review) {
		repository.delete(review);
	}

	public void addReviewSpecialist(Specialist specialist, int rating, String description) {
		var review = Review.builder()
				.setRating(rating)
				.setSpecialist(specialist)
				.setDescription(description)
				.build();

		this.create(review);
		specialist.addRating(rating);

		specialistService.update(specialist);
	}

	public boolean exists(Review review) {
		return repository.exists(Example.of(review));
	}

	public Review findById(Long id) {
		return repository.findById(id).orElseThrow(InvalidEntityException::new);
	}
}
