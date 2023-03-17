package dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity @Getter @Setter
@SuperBuilder(setterPrefix = "set")
public class ConfirmationToken extends BaseEntity<Long> {

	@Temporal(value = TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private LocalDateTime creationDate;

	private String confirmationToken;

	private boolean used;

	public ConfirmationToken() {
		this.confirmationToken = UUID.randomUUID().toString();
		this.creationDate = LocalDateTime.now();
	}
}
