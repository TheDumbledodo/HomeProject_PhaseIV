package dev.dumble.homeproject.HomeProject_PhaseIV.service.impl;

import dev.dumble.homeproject.HomeProject_PhaseIV.dto.AssistanceGroupDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.AssistanceGroup;
import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.InvalidEntityException;
import dev.dumble.homeproject.HomeProject_PhaseIV.mapper.AssistanceGroupMapper;
import dev.dumble.homeproject.HomeProject_PhaseIV.repository.IAssistanceGroupRepository;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.GenericService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AssistanceGroupService extends GenericService<Long, IAssistanceGroupRepository, AssistanceGroup> {

	public AssistanceGroupService(IAssistanceGroupRepository repository) {
		super(repository);
	}

	@Override
	public AssistanceGroup read(AssistanceGroup group) {
		return super.getRepository()
				.findAssistanceGroupByName(group.getName())
				.orElseThrow(() -> new InvalidEntityException("The assistance groups name couldn't be found in the database."));
	}

	public Set<AssistanceGroupDTO> findAllSerialized() {
		return super.getRepository().findAll()
				.stream()
				.map(group -> AssistanceGroupMapper.getInstance().serialize(group))
				.collect(Collectors.toSet());
	}
}
