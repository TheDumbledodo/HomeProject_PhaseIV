package dev.dumble.homeproject.HomeProject_PhaseIV.filter.enums;

import dev.dumble.homeproject.HomeProject_PhaseIV.filter.request.SortRequest;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;

public enum SortDirection {
	ASC {
		@Override
		public <T> Order build(Root<T> root, CriteriaBuilder criteriaBuilder, SortRequest request) {
			return criteriaBuilder.asc(root.get(request.getKey()));
		}
	},
	DESC {
		@Override
		public <T> Order build(Root<T> root, CriteriaBuilder criteriaBuilder, SortRequest request) {
			return criteriaBuilder.desc(root.get(request.getKey()));
		}
	};

	public abstract <T> Order build(Root<T> root, CriteriaBuilder criteriaBuilder, SortRequest request);
}