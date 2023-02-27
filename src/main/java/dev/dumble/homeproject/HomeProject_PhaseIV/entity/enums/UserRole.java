package dev.dumble.homeproject.HomeProject_PhaseIV.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {
	MANAGER("مدیر", 2),
	SPECIALIST("متخصص", 1),
	CLIENT("مشتری", 0);

	private final String name;

	private final int code;
}
