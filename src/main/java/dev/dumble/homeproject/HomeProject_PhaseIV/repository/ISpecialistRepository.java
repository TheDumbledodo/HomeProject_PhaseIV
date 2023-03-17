package dev.dumble.homeproject.HomeProject_PhaseIV.repository;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.users.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISpecialistRepository extends JpaRepository<Specialist, Long>, JpaSpecificationExecutor<Specialist> {

	Optional<Specialist> findSpecialistByUsername(String username);

	Optional<Specialist> findSpecialistByEmailAddress(String email);

	@Query("FROM Specialist WHERE token.id = :tokenId")
	Optional<Specialist> findSpecialistByConfirmationToken(@Param(value = "tokenId") Long tokenId);
}
