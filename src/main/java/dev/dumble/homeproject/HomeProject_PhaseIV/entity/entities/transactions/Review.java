package dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.transactions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.Transaction;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.users.Specialist;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
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

	@NotNull @Column(nullable = false)
	private int rating;

	@JsonIgnore @ManyToOne
	private Specialist specialist;
}
