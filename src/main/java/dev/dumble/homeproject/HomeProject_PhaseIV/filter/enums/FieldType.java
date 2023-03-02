package dev.dumble.homeproject.HomeProject_PhaseIV.filter.enums;

import dev.dumble.homeproject.HomeProject_PhaseIV.utils.Utility;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
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
	STRING {
		@Override
		public Object parse(String value) {
			return value;
		}
	};

	public abstract Object parse(String value);
}