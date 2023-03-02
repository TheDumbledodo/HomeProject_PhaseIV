package dev.dumble.homeproject.HomeProject_PhaseIV.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.ConfirmationToken;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.enums.UserRole;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@DiscriminatorColumn(name = "user_code", discriminatorType = DiscriminatorType.INTEGER)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter @Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(setterPrefix = "set")
public abstract class UserEntity extends BaseEntity<Long> implements UserDetails {

	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private ConfirmationToken token;

	@Temporal(value = TemporalType.TIMESTAMP)
	private LocalDateTime registeredTime;

	@Enumerated(value = EnumType.STRING)
	private UserRole userRole;

	@Column(unique = true)
	private String emailAddress;

	@JsonIgnore
	@Column(length = 60)
	private String password;

	private String username, firstName, lastName;

	private Long credit;

	private boolean enabled;

	@Override @JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		var role = this.getUserRole().name();

		return List.of(new SimpleGrantedAuthority(role));
	}

	@Override @JsonIgnore
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override @JsonIgnore
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override @JsonIgnore
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
