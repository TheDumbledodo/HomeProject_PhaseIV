package dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.transactions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.Transaction;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.members.Client;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.services.Assistance;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.enums.RequestStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter @Setter
@SuperBuilder(setterPrefix = "set")
public class Request extends Transaction {

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "request")
	private List<Offer> offers;

	@OneToOne
	private Offer acceptedOffer;

	@NotNull @Column(nullable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	private LocalDateTime dueTime;

	@NotNull @Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private RequestStatus status;

	@OneToOne
	private Assistance assistance;

	@NotNull @Column(nullable = false)
	private Long offeredPrice;

	@NotNull @Column(nullable = false)
	private String address;

	@JsonIgnore @ManyToOne
	private Client client;

	public void addOffer(Offer offer) {
		offers.add(offer);
	}
}
