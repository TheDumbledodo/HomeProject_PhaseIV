package dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(setterPrefix = "set")
public class AssistanceGroup extends BaseEntity<Long> {

	@Default
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "group")
	private List<Assistance> assistanceList = new ArrayList<>();

	@Column(unique = true)
	private String name;
}
