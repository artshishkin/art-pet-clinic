package com.artarkatesoft.artpetclinic.repositories;

import com.artarkatesoft.artpetclinic.model.PetType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PetTypeRepository extends CrudRepository<PetType, Long> {
    Optional<PetType> findOneByName(String name);
}
