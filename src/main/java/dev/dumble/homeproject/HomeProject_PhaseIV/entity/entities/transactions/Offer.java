package dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.transactions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.Transaction;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.members.Specialist;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter @Setter
@SuperBuilder(setterPrefix = "set")
public class Offer extends Transaction {

	@JsonIgnore @ManyToOne
	private Specialist specialist;

	@JsonIgnore @OneToOne
	private Request acceptedRequest;

	@JsonIgnore @ManyToOne
	private Request request;

	@Temporal(value = TemporalType.TIMESTAMP)
	private LocalDateTime creationTime;

	@Temporal(value = TemporalType.TIMESTAMP)
	private LocalDateTime startTime;

	private Integer practicalDays;
	private Long offeredPrice;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof Offer offer)) return false;
		return offer.getDescription().equalsIgnoreCase(this.getDescription()) &&
				offer.getOfferedPrice().equals(this.getOfferedPrice()) &&
				offer.getPracticalDays().equals(this.getPracticalDays()) &&
				offer.getStartTime().equals(this.getStartTime());
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
