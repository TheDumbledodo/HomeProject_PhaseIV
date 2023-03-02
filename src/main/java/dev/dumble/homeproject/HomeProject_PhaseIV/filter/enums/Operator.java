package dev.dumble.homeproject.HomeProject_PhaseIV.filter.enums;

import dev.dumble.homeproject.HomeProject_PhaseIV.filter.request.FilterRequest;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.time.LocalDateTime;

public enum Operator {
	EQUAL {
		@Override
		public <T> Predicate build(Root<T> root, CriteriaBuilder criteriaBuilder, FilterRequest request, Predicate predicate) {
			var value = request.getFieldType().parse(request.getValue().toString());
			var key = root.get(request.getKey());

			return criteriaBuilder.and(criteriaBuilder.equal(key, value), predicate);
		}
	},
	NOT_EQUAL {
		@Override
		public <T> Predicate build(Root<T> root, CriteriaBuilder criteriaBuilder, FilterRequest request, Predicate predicate) {
			var value = request.getFieldType().parse(request.getValue().toString());
			var key = root.get(request.getKey());

			return criteriaBuilder.and(criteriaBuilder.notEqual(key, value), predicate);
		}
	},
	LIKE {
		@Override
		public <T> Predicate build(Root<T> root, CriteriaBuilder criteriaBuilder, FilterRequest request, Predicate predicate) {
			var value = request.getValue().toString().toUpperCase();

			Expression<String> key = root.get(request.getKey());
			return criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(key), "%%%s%%".formatted(value)), predicate);
		}
	},
	IN {
		@Override
		public <T> Predicate build(Root<T> root, CriteriaBuilder criteriaBuilder, FilterRequest request, Predicate predicate) {
			var values = request.getValues();
			var inClause = criteriaBuilder.in(root.get(request.getKey()));

			values.stream()
					.map(value -> request.getFieldType().parse(value.toString()))
					.forEach(inClause::value);

			return criteriaBuilder.and(inClause, predicate);
		}
	},
	GREATER_THAN {
		@Override
		public <T> Predicate build(Root<T> root, CriteriaBuilder builder, FilterRequest request, Predicate predicate) {

			var value = request.getFieldType().parse(request.getValue().toString());

			var fieldType = request.getFieldType();
			return switch (fieldType) {
				case INTEGER -> builder.and(builder.greaterThan(root.get(request.getKey()), (Integer) value));
				case DOUBLE -> builder.and(builder.greaterThan(root.get(request.getKey()), (Double) value));
				case LONG -> builder.and(builder.greaterThan(root.get(request.getKey()), (Long) value));
				default -> predicate;
			};
		}
	},
	LOWER_THAN {
		@Override
		public <T> Predicate build(Root<T> root, CriteriaBuilder builder, FilterRequest request, Predicate predicate) {
			var value = request.getFieldType().parse(request.getValue().toString());

			var fieldType = request.getFieldType();
			return switch (fieldType) {
				case INTEGER -> builder.and(builder.lessThan(root.get(request.getKey()), (Integer) value));
				case DOUBLE -> builder.and(builder.lessThan(root.get(request.getKey()), (Double) value));
				case LONG -> builder.and(builder.lessThan(root.get(request.getKey()), (Long) value));
				default -> predicate;
			};
		}
	},
	BETWEEN {
		@Override
		public <T> Predicate build(Root<T> root, CriteriaBuilder criteriaBuilder, FilterRequest request, Predicate predicate) {

			var value = request.getFieldType().parse(request.getValue().toString());
			var valueTo = request.getFieldType().parse(request.getValueTo().toString());

			if (request.getFieldType() == FieldType.DATE) {
				var startDate = (LocalDateTime) value;
				var endDate = (LocalDateTime) valueTo;

				Expression<LocalDateTime> key = root.get(request.getKey());

				return criteriaBuilder.and(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(key, startDate), criteriaBuilder.lessThanOrEqualTo(key, endDate)), predicate);
			}

			if (request.getFieldType() != FieldType.CHAR && request.getFieldType() != FieldType.BOOLEAN) {
				var start = (Number) value;
				var end = (Number) valueTo;

				Expression<Number> key = root.get(request.getKey());

				return criteriaBuilder.and(criteriaBuilder.and(criteriaBuilder.ge(key, start), criteriaBuilder.le(key, end)), predicate);
			}
			return predicate;
		}
	};

	public abstract <T> Predicate build(Root<T> root, CriteriaBuilder criteriaBuilder, FilterRequest request, Predicate predicate);
}