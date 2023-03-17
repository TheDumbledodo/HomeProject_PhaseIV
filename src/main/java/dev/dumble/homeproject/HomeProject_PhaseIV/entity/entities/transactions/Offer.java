package dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.transactions;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.Transaction;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.users.Specialist;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter @Setter
@SuperBuilder(setterPrefix = "set")
public class Offer extends Transaction {

	@ManyToOne private Specialist specialist;
	@ManyToOne private Request request;
	@OneToOne private Request acceptedRequest;

	@Temporal(value = TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private LocalDateTime creationTime, startTime;

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
}
