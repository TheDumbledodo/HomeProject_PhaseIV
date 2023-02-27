package dev.dumble.homeproject.HomeProject_PhaseIV.service.impl;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.services.AssistanceGroup;
import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.InvalidEntityException;
import dev.dumble.homeproject.HomeProject_PhaseIV.repository.IAssistanceGroupRepository;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.GenericService;
import org.springframework.stereotype.Service;

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
}
