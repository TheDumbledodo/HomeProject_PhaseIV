package dev.dumble.homeproject.HomeProject_PhaseIV.service;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.services.Assistance;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.services.AssistanceGroup;
import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.DuplicateEntityException;
import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.InvalidEntityException;
import dev.dumble.homeproject.HomeProject_PhaseIV.repository.IAssistanceGroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AssistanceGroupService {

	private final IAssistanceGroupRepository repository;

	public AssistanceGroup create(AssistanceGroup group) {

		if (repository.exists(Example.of(group)))
			throw new DuplicateEntityException();

		return repository.save(group);
	}

	public AssistanceGroup read(AssistanceGroup group) {
		return repository
				.findAssistanceGroupByName(group.getName())
				.orElseThrow(InvalidEntityException::new);
	}

	public AssistanceGroup update(AssistanceGroup group) {
		var id = group.getId();
		if (id == null)
			throw new InvalidEntityException();

		if (repository.findById(id).isEmpty())
			throw new InvalidEntityException();

		return repository.save(group);
	}

	public void delete(AssistanceGroup group) {
		repository.delete(group);
	}

	public boolean exists(AssistanceGroup group) {
		return group.getId() != null && repository.exists(Example.of(group));
	}

	public List<AssistanceGroup> findAllGroups() {
		return repository.findAll();
	}

	public List<Assistance> findAllAssistances(AssistanceGroup assistanceGroup) {
		return assistanceGroup.getAssistanceList();
	}

	public AssistanceGroup findById(Long id) {
		return repository.findById(id).orElseThrow(InvalidEntityException::new);
	}
}
