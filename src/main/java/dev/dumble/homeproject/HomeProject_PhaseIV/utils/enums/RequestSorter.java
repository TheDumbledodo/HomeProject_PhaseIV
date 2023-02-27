package dev.dumble.homeproject.HomeProject_PhaseIV.utils.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RequestSorter {
	SORT_PRICE("Ascending sorting by offer price."),
	SORT_SPECIALIST_RATING("Ascending sorting by offer's specialist rating."),
	SORT_NONE("Natural sorting.");

	private final String name;
}
