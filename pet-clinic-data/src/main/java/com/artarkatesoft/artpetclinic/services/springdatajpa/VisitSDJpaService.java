package com.artarkatesoft.artpetclinic.services.springdatajpa;

import com.artarkatesoft.artpetclinic.model.Visit;
import com.artarkatesoft.artpetclinic.repositories.VisitRepository;
import com.artarkatesoft.artpetclinic.services.VisitService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("springdatajpa")
public class VisitSDJpaService extends AbstractSDJpaService<Visit, Long> implements VisitService {

    public VisitSDJpaService(VisitRepository repository) {
        super(repository);
    }
}
