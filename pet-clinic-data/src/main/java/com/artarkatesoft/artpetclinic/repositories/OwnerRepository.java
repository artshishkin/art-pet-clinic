package com.artarkatesoft.artpetclinic.repositories;

import com.artarkatesoft.artpetclinic.model.Owner;
import org.springframework.data.repository.CrudRepository;

public interface OwnerRepository extends CrudRepository<Owner, Long> {
}
