package dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.transactions;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.Transaction;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.users.Specialist;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@Getter @Setter
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(setterPrefix = "set")
public class Review extends Transaction {

	@ManyToOne
	private Specialist specialist;

	private int rating;
}
