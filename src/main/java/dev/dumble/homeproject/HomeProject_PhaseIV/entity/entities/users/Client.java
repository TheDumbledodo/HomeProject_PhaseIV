package dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "client", fetch = FetchType.EAGER)
	private List<Request> requests;

	public void addRequest(Request request) {
		requests.add(request);
	}
}
