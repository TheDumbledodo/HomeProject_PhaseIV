package dev.dumble.homeproject.HomeProject_PhaseIV.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.io.Serializable;
import java.util.Objects;

@Getter @Setter
@MappedSuperclass
@AllArgsConstructor @NoArgsConstructor
@SuperBuilder(setterPrefix = "set")
public abstract class BaseEntity<ID extends Serializable> {

	@Id
	@GenericGenerator(name = "sequenceGen",
			strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
			parameters = {
					@Parameter(name = "sequence_name", value = "SEQUENCE")
			})
	@GeneratedValue(generator = "sequence_name")
	public ID id;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof BaseEntity<?> baseEntity)) return false;
		return Objects.equals(this.getId(), baseEntity.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getId());
	}
}
