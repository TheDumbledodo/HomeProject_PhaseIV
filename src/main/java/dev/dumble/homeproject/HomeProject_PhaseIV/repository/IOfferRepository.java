package dev.dumble.homeproject.HomeProject_PhaseIV.repository;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.transactions.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface IOfferRepository extends JpaRepository<Offer, Long> {

	@Query(value =
			"FROM Offer WHERE acceptedRequest = null AND description ILIKE :description AND request.id = :requestId AND specialist.id = :specialistId")
	Optional<Offer> findMatchingOffers(@Param(value = "description") String description,
									   @Param(value = "requestId") Long requestId,
									   @Param(value = "specialistId") Long specialistId);

	@Query(value = "FROM Offer WHERE request.id = :requestId ORDER BY offeredPrice ASC")
	Set<Offer> findRequestOffersByPrice(@Param(value = "requestId") Long requestId);

	@Query(value = "FROM Offer WHERE request.id = :requestId ORDER BY specialist.rating ASC")
	Set<Offer> findRequestOffersBySpecialistRating(@Param(value = "requestId") Long requestId);
}
