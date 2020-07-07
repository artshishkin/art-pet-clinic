package com.artarkatesoft.artpetclinic.services.springdatajpa;

import com.artarkatesoft.artpetclinic.model.PetType;
import com.artarkatesoft.artpetclinic.services.PetTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
@Profile("springdatajpa")
public class PetTypeSDJpaService extends AbstractSDJpaService<PetType, Long> implements PetTypeService {
    public PetTypeSDJpaService(CrudRepository<PetType, Long> repository) {
        super(repository);
    }
}
