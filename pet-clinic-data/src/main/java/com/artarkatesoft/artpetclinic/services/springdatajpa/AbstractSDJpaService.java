package com.artarkatesoft.artpetclinic.services.springdatajpa;

import com.artarkatesoft.artpetclinic.model.BaseEntity;
import com.artarkatesoft.artpetclinic.services.CrudService;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class AbstractSDJpaService<T extends BaseEntity, ID extends Long> implements CrudService<T, ID> {

    private final CrudRepository<T, ID> repository;

    public AbstractSDJpaService(CrudRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    public T findById(ID id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public T save(T object) {
        return repository.save(object);
    }

    @Override
    public Set<T> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .collect(Collectors.toSet());
    }

    @Override
    public void delete(T object) {
        repository.delete(object);
    }

    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);
    }
}
