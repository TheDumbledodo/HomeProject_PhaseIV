package dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

	@Default @JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "group")
	private List<Assistance> assistanceList = new ArrayList<>();

	@NotNull @Column(unique = true, nullable = false)
	private String name;
}
