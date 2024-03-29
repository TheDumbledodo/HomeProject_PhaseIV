package dev.dumble.homeproject.HomeProject_PhaseIV.service.impl;

import dev.dumble.homeproject.HomeProject_PhaseIV.dto.AssistanceDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.Assistance;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.AssistanceGroup;
import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.DuplicateEntityException;
import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.InvalidEntityException;
import dev.dumble.homeproject.HomeProject_PhaseIV.mapper.AssistanceMapper;
import dev.dumble.homeproject.HomeProject_PhaseIV.repository.IAssistanceRepository;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.GenericService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AssistanceService extends GenericService<Long, IAssistanceRepository, Assistance> {

	public AssistanceService(IAssistanceRepository repository) {
		super(repository);
	}

	@Override
	public Assistance create(Assistance entity) {
		var name = entity.getName();

		if (super.getRepository().findAssistanceByName(name).isPresent())
			throw new DuplicateEntityException("This assistance entity is already in the database.");

		return this.getRepository().save(entity);
	}

	@Override
	public Assistance read(Assistance assistance) {
		return super.getRepository()
				.findAssistanceByName(assistance.getName())
				.orElseThrow(() -> new InvalidEntityException("The assistances name couldn't be found in the database."));
	}

	public Set<AssistanceDTO> findAllSerialized(AssistanceGroup group) {
		return group.getAssistanceList()
				.stream()
				.map(assistance -> AssistanceMapper.getInstance().serialize(assistance))
				.collect(Collectors.toSet());
	}
}
