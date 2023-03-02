package dev.dumble.homeproject.HomeProject_PhaseIV.mapper;

import dev.dumble.homeproject.HomeProject_PhaseIV.dto.UserDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.users.Specialist;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SpecialistMapper {

	SpecialistMapper instance = Mappers.getMapper(SpecialistMapper.class);

	Specialist map(UserDTO userDTO);

	static SpecialistMapper getInstance() {
		return instance;
	}
}
