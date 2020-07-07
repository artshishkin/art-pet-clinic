package com.artarkatesoft.artpetclinic.services.springdatajpa;

import com.artarkatesoft.artpetclinic.model.Specialty;
import com.artarkatesoft.artpetclinic.services.SpecialtyService;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
@Profile("springdatajpa")
public class SpecialtySPJpaService extends AbstractSDJpaService<Specialty,Long> implements SpecialtyService {

    public SpecialtySPJpaService(CrudRepository<Specialty, Long> repository) {
        super(repository);
    }
}
