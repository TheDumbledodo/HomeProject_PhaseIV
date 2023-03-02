package dev.dumble.homeproject.HomeProject_PhaseIV.repository;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.members.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IClientRepository extends JpaRepository<Client, Long>, JpaSpecificationExecutor<Client> {

	Optional<Client> findClientByUsername(@Param(value = "username") String username);

	Optional<Client> findClientByEmailAddress(String email);
}
