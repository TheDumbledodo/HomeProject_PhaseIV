package dev.dumble.homeproject.HomeProject_PhaseIV.repository;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.members.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISpecialistRepository extends JpaRepository<Specialist, Long>, JpaSpecificationExecutor<Specialist> {

	Optional<Specialist> findSpecialistByUsername(String username);

	Optional<Specialist> findClientByEmailAddress(String email);
}
