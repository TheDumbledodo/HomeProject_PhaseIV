package dev.dumble.homeproject.HomeProject_PhaseIV.repository;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.members.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IManagerRepository extends JpaRepository<Manager, Long> {

	Optional<Manager> findManagerByUsername(String username);

	Optional<Manager> findManagerByEmailAddress(String email);
}
