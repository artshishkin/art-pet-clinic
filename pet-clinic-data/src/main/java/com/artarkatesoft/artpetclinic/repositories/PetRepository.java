package com.artarkatesoft.artpetclinic.repositories;

import com.artarkatesoft.artpetclinic.model.Pet;
import org.springframework.data.repository.CrudRepository;

public interface PetRepository extends CrudRepository<Pet,Long> {
}
