package dev.dumble.homeproject.HomeProject_PhaseIV.mapper;

import dev.dumble.homeproject.HomeProject_PhaseIV.dto.AssistanceGroupDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.AssistanceGroup;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AssistanceGroupMapper {

	AssistanceGroupMapper instance = Mappers.getMapper(AssistanceGroupMapper.class);

	AssistanceGroup map(AssistanceGroupDTO AssistanceGroupDTO);

	static AssistanceGroupMapper getInstance() {
		return instance;
	}
}
