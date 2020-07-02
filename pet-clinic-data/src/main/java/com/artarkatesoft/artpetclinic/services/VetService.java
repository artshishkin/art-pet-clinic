package com.artarkatesoft.artpetclinic.services;

import com.artarkatesoft.artpetclinic.model.Vet;

import java.util.Set;

public interface VetService {

    Vet findById(Long id);

    Vet save(Vet owner);

    Set<Vet> findAll();
}
