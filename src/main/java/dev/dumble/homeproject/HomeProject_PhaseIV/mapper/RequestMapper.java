package dev.dumble.homeproject.HomeProject_PhaseIV.mapper;

import dev.dumble.homeproject.HomeProject_PhaseIV.dto.RequestDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.transactions.Request;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RequestMapper {

	RequestMapper instance = Mappers.getMapper(RequestMapper.class);

	Request map(RequestDTO requestDTO);

	static RequestMapper getInstance() {
		return instance;
	}
}
