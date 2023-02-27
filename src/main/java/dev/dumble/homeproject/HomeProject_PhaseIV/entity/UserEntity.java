package dev.dumble.homeproject.HomeProject_PhaseIV.entity;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.enums.UserRole;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@DiscriminatorColumn(name = "user_code", discriminatorType = DiscriminatorType.INTEGER)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter @Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(setterPrefix = "set")
public abstract class UserEntity extends BaseEntity<Long> {

	@Temporal(value = TemporalType.TIMESTAMP)
	private LocalDateTime registeredTime;

	@Enumerated(value = EnumType.STRING)
	private UserRole userRole;

	@Column(unique = true)
	private String emailAddress;

	private String password;

	private String username;

	private String firstName;

	private String lastName;

	private Long credit;
}
