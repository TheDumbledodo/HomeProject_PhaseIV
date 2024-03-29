package dev.dumble.homeproject.HomeProject_PhaseIV.filter.enums;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.enums.RequestStatus;
import dev.dumble.homeproject.HomeProject_PhaseIV.utils.Utility;

import java.time.LocalDateTime;

public enum FieldType {
	DATE {
		@Override
		public Object parse(String value) {
			try {
				return LocalDateTime.parse(value, Utility.formatter);

			} catch (Exception exception) {
				return null;
			}
		}
	},
	BOOLEAN {
		@Override
		public Object parse(String value) {
			return Boolean.valueOf(value);
		}
	},
	DOUBLE {
		@Override
		public Object parse(String value) {
			return Double.valueOf(value);
		}
	},
	INTEGER {
		@Override
		public Object parse(String value) {
			return Integer.valueOf(value);
		}
	},
	LONG {
		@Override
		public Object parse(String value) {
			return Long.valueOf(value);
		}
	},
	CHAR {
		@Override
		public Object parse(String value) {
			return value.charAt(0);
		}
	},
	// todo: this could be improved after phase IV
	ENUM {
		@Override
		public Object parse(String value) {
			return Enum.valueOf(RequestStatus.class, value.toUpperCase());
		}
	},
	STRING {
		@Override
		public Object parse(String value) {
			return value;
		}
	};

	public abstract Object parse(String value);
}