package dev.dumble.homeproject.HomeProject_PhaseIV.mapper;

import dev.dumble.homeproject.HomeProject_PhaseIV.dto.ManagerDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.users.Manager;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ManagerMapper {

	ManagerMapper instance = Mappers.getMapper(ManagerMapper.class);

	Manager map(ManagerDTO managerDTO);

	ManagerDTO serialize(Manager manager);

	static ManagerMapper getInstance() {
		return instance;
	}
}
