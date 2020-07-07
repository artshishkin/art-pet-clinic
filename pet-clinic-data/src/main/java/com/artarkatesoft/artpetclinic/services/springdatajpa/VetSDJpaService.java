package com.artarkatesoft.artpetclinic.services.springdatajpa;

import com.artarkatesoft.artpetclinic.model.Vet;
import com.artarkatesoft.artpetclinic.services.VetService;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
@Profile("springdatajpa")
public class VetSDJpaService extends AbstractSDJpaService<Vet, Long> implements VetService {

    public VetSDJpaService(CrudRepository<Vet, Long> repository) {
        super(repository);
    }
}
