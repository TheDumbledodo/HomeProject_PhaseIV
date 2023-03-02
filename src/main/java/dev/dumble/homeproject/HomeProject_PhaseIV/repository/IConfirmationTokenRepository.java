package dev.dumble.homeproject.HomeProject_PhaseIV.repository;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

	Optional<ConfirmationToken> findByConfirmationToken(String confirmationToken);
}
