package dev.dumble.homeproject.HomeProject_PhaseIV.mapper;

import dev.dumble.homeproject.HomeProject_PhaseIV.dto.AssistanceDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.Assistance;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AssistanceMapper {

	AssistanceMapper instance = Mappers.getMapper(AssistanceMapper.class);

	Assistance map(AssistanceDTO assistanceDTO);

	AssistanceDTO serialize(Assistance assistance);

	static AssistanceMapper getInstance() {
		return instance;
	}
}
