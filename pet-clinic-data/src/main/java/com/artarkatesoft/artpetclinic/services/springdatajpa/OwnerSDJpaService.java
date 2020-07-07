package com.artarkatesoft.artpetclinic.services.springdatajpa;

import com.artarkatesoft.artpetclinic.model.Owner;
import com.artarkatesoft.artpetclinic.repositories.OwnerRepository;
import com.artarkatesoft.artpetclinic.services.OwnerService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("springdatajpa")
public class OwnerSDJpaService extends AbstractSDJpaService<Owner, Long> implements OwnerService {

    public OwnerSDJpaService(OwnerRepository repository) {
        super(repository);
    }

    @Override
    public Owner findByLastName(String lastName) {
        return ((OwnerRepository) repository).findByLastName(lastName);
    }
}
