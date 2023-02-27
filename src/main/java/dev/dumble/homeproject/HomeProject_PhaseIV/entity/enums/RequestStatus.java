package dev.dumble.homeproject.HomeProject_PhaseIV.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RequestStatus {
	AWAITING_SUGGESTION("منتظر پیشنهاد متخصصان"),
	AWAITING_SELECTION("منتظر انتخاب متخصص"),
	AWAITING_START("منتظر امدن متخصص به محل شما"),
	STARTED("شروع شده"),
	DONE("انجام شده"),
	PAID("پرداخت شده");

	private final String name;

	public static RequestStatus from(String name) {
		name = name.toUpperCase();

		for (var value : RequestStatus.values())
			if (value.name().equalsIgnoreCase(name)) return value;

		throw new IllegalArgumentException("The enumeration [%s] you provided was wrong.".formatted(name));
	}
}
