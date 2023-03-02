package dev.dumble.homeproject.HomeProject_PhaseIV.filter;

import dev.dumble.homeproject.HomeProject_PhaseIV.filter.request.SearchRequest;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

@AllArgsConstructor
public class SearchSpecification<T> implements Specification<T> {

	private final transient SearchRequest request;

	@Override
	public Predicate toPredicate(@NonNull Root<T> root, @NonNull CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		var predicate = criteriaBuilder.equal(criteriaBuilder.literal(Boolean.TRUE), Boolean.TRUE);

		for (var filter : this.request.getFilters())
			predicate = filter.getOperator().build(root, criteriaBuilder, filter, predicate);

		var orders = this.request.getSorts()
				.stream()
				.map(sort -> sort.getDirection().build(root, criteriaBuilder, sort))
				.toList();

		query.orderBy(orders);
		return predicate;
	}
}