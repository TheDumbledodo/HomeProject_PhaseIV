package dev.dumble.homeproject.HomeProject_PhaseIV.mappers;

import dev.dumble.homeproject.HomeProject_PhaseIV.dto.ManagerDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.members.Manager;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ManagerMapper {

	ManagerMapper instance = Mappers.getMapper(ManagerMapper.class);

	Manager map(ManagerDTO managerDTO);

	static ManagerMapper getInstance() {
		return instance;
	}
}
