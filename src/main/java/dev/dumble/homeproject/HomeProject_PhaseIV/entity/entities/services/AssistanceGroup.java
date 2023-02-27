package dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "group")
	@Default private List<Assistance> assistanceList = new ArrayList<>();

	@Column(unique = true)
	private String name;
}
