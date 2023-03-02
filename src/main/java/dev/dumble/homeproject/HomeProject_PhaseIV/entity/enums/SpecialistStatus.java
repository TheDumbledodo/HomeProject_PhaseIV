package dev.dumble.homeproject.HomeProject_PhaseIV.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SpecialistStatus {
	DISABLED("غیر فعال"),
	RECENT("جدید"),
	PENDING_CONFIRMATION("در انتظار تایید"),
	ACCEPTED("تایید شده");

	private final String name;
}
