package dev.dumble.homeproject.HomeProject_PhaseIV.service;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.services.Assistance;
import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.DuplicateEntityException;
import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.InvalidEntityException;
import dev.dumble.homeproject.HomeProject_PhaseIV.repository.IAssistanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssistanceService {

	private final AssistanceGroupService groupService;

	private final IAssistanceRepository repository;

	public Assistance create(Assistance assistance) {
		var group = groupService.read(assistance.getGroup());

		if (group == null || !groupService.exists(group))
			throw new InvalidEntityException();

		if (repository.exists(Example.of(assistance)))
			throw new DuplicateEntityException();

		assistance.setGroup(group);
		return repository.save(assistance);
	}

	public Assistance read(Assistance assistance) {
		return repository
				.findAssistanceByName(assistance.getName())
				.orElseThrow(InvalidEntityException::new);
	}

	public Assistance update(Assistance assistance) {
		var id = assistance.getId();
		if (id == null)
			throw new InvalidEntityException("You haven't entered any assistance ids.");

		if (repository.findById(id).isEmpty())
			throw new InvalidEntityException("There were no entities found with that id.");

		return repository.save(assistance);
	}

	public void delete(Assistance assistance) {
		repository.delete(assistance);
	}

	public Assistance updateMinimumPrice(Assistance assistance, Long minimumPrice) {
		assistance.setMinimumPrice(minimumPrice);

		return this.update(assistance);
	}

	public Assistance updateDescription(Assistance assistance, String description) {
		assistance.setDescription(description);

		return this.update(assistance);
	}

	public Assistance findById(Long id) {
		return repository.findById(id).orElseThrow(InvalidEntityException::new);
	}
}
