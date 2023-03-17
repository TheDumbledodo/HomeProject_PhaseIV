package dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.users;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.UserEntity;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.transactions.Request;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@DiscriminatorValue(value = "0")
@Getter @Setter @NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(setterPrefix = "set")
public class Client extends UserEntity {

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "client", fetch = FetchType.EAGER)
	private List<Request> requests;

	private long submittedRequests;
	private long finishedRequests;

	public void addRequest(Request request) {
		requests.add(request);
		submittedRequests += 1;
	}

	public void incrementFinishedRequests() {
		this.finishedRequests += 1;
	}
}
