package dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.services;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.BaseEntity;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.members.Specialist;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Entity
@ToString
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@SuperBuilder(setterPrefix = "set")
public class Assistance extends BaseEntity<Long> {

	@Column(unique = true)
	private String name;

	@Column(unique = true)
	private String description;

	@ManyToOne
	private AssistanceGroup group;

	@ManyToMany
	private Set<Specialist> specialistList;

	private Long minimumPrice;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof Assistance assistance)) return false;
		return assistance.getName().equalsIgnoreCase(this.name) &&
				assistance.getDescription().equalsIgnoreCase(this.getDescription());
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
