package com.artarkatesoft.artpetclinic.services.springdatajpa;

import com.artarkatesoft.artpetclinic.model.Pet;
import com.artarkatesoft.artpetclinic.repositories.PetRepository;
import com.artarkatesoft.artpetclinic.services.PetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("springdatajpa")
public class PetSDJpaService extends AbstractSDJpaService<Pet, Long> implements PetService {

    public PetSDJpaService(PetRepository repository) {
        super(repository);
    }
}
