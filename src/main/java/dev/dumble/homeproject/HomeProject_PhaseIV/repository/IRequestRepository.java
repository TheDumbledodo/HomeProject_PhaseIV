package dev.dumble.homeproject.HomeProject_PhaseIV.repository;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.Assistance;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.transactions.Request;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface IRequestRepository extends JpaRepository<Request, Long> {

	@Query(value = "FROM Request WHERE status = 'AWAITING_SELECTION' OR status = 'AWAITING_SUGGESTION' AND assistance IN :assistanceSet")
	Set<Request> findMatchingRequests(@Param(value = "assistanceSet") Set<Assistance> assistanceSet);

	@Query(value = "FROM Request WHERE status = :requestStatus AND client.id = :clientId")
	Set<Request> findClientRequests(@Param(value = "requestStatus") RequestStatus requestStatus,
									@Param(value = "clientId") Long clientId);
}
