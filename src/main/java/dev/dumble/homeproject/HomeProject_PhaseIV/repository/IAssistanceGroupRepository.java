package dev.dumble.homeproject.HomeProject_PhaseIV.repository;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.AssistanceGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAssistanceGroupRepository extends JpaRepository<AssistanceGroup, Long> {

	Optional<AssistanceGroup> findAssistanceGroupByName(String name);
}
