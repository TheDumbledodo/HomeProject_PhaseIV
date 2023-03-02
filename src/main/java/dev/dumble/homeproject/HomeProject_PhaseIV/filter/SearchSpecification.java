package dev.dumble.homeproject.HomeProject_PhaseIV.filter;

import dev.dumble.homeproject.HomeProject_PhaseIV.filter.request.SearchRequest;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import java.io.Serial;

@Slf4j
@AllArgsConstructor
public class SearchSpecification<T> implements Specification<T> {

	@Serial
	private static final long serialVersionUID = -9153865343320750644L;

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