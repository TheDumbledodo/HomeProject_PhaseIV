package dev.dumble.homeproject.HomeProject_PhaseIV.service;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.BaseEntity;
import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.DuplicateEntityException;
import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.InvalidEntityException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

@Getter
@AllArgsConstructor
@SuppressWarnings("unused")
public abstract class GenericService<S extends Serializable, R extends JpaRepository<E, S>, E extends BaseEntity<S>> {

	private R repository;

	public E create(E entity) {
		if (this.exists(entity))
			throw new DuplicateEntityException();

		return this.getRepository().save(entity);
	}

	public void delete(E entity) {
		repository.delete(entity);
	}

	public boolean exists(E entity) {
		return repository.exists(Example.of(entity));
	}

	public List<E> findAll() {
		return repository.findAll();
	}

	public E read(E entity) {
		return repository.findOne(Example.of(entity)).orElseThrow(InvalidEntityException::new);
	}

	public E findById(S id) {
		return repository.findById(id).orElseThrow(InvalidEntityException::new);
	}

	public void update(E entity) {
		var id = entity.getId();
		if (id == null || repository.findById(id).isEmpty())
			throw new InvalidEntityException();

		repository.save(entity);
	}
}
