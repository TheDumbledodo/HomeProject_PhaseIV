package dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.transactions;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.Transaction;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.Assistance;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.users.Client;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter @Setter
@SuperBuilder(setterPrefix = "set")
public class Request extends Transaction {

	@ManyToOne private Client client;
	@OneToOne private Offer acceptedOffer;
	@OneToOne private Assistance assistance;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "request")
	private List<Offer> offers;

	@Temporal(value = TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private LocalDateTime dueTime;

	@Enumerated(value = EnumType.STRING)
	private RequestStatus status;

	private Long offeredPrice;
	private String address;

	public void addOffer(Offer offer) {
		offers.add(offer);
	}
}
