package dev.dumble.homeproject.HomeProject_PhaseIV.filter.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data @Builder(setterPrefix = "set")
@NoArgsConstructor @AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SearchRequest implements Serializable {

	private List<FilterRequest> filters;
	private List<SortRequest> sorts;

	private Integer size;

	public List<FilterRequest> getFilters() {
		return Objects.isNull(this.filters) ? new ArrayList<>() : this.filters;
	}

	public List<SortRequest> getSorts() {
		return Objects.isNull(this.sorts) ? new ArrayList<>() : this.sorts;
	}
}