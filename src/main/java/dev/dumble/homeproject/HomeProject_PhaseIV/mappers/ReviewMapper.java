package dev.dumble.homeproject.HomeProject_PhaseIV.mappers;

import dev.dumble.homeproject.HomeProject_PhaseIV.dto.ReviewDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.transactions.Review;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReviewMapper {

	ReviewMapper instance = Mappers.getMapper(ReviewMapper.class);

	Review map(ReviewDTO reviewDTO);

	static ReviewMapper getInstance() {
		return instance;
	}
}
