package dev.dumble.homeproject.HomeProject_PhaseIV.mapper;

import dev.dumble.homeproject.HomeProject_PhaseIV.dto.UserDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.users.Client;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientMapper {

	ClientMapper instance = Mappers.getMapper(ClientMapper.class);

	Client map(UserDTO userDTO);

	static ClientMapper getInstance() {
		return instance;
	}
}
