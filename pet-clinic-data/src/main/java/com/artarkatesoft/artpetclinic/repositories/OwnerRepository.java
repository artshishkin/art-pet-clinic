package com.artarkatesoft.artpetclinic.repositories;

import com.artarkatesoft.artpetclinic.model.Owner;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OwnerRepository extends CrudRepository<Owner, Long> {
    Owner findOneByLastName(String lastName);
    List<Owner> findAllByLastNameContains(String lastName);
}
