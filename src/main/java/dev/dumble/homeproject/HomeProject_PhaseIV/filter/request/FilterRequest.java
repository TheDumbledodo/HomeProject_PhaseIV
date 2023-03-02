package dev.dumble.homeproject.HomeProject_PhaseIV.filter.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import dev.dumble.homeproject.HomeProject_PhaseIV.filter.enums.FieldType;
import dev.dumble.homeproject.HomeProject_PhaseIV.filter.enums.Operator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data @Builder(setterPrefix = "set")
@NoArgsConstructor @AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FilterRequest implements Serializable {

	private String key;
	private Operator operator;
	private FieldType fieldType;

	private transient Object value;
	private transient Object valueTo;
	private transient List<Object> values;
}