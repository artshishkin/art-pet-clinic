package com.artarkatesoft.artpetclinic.services;

import com.artarkatesoft.artpetclinic.model.Pet;

import java.util.Set;

public interface PetService {

    Pet findById(Long id);

    Pet save(Pet owner);

    Set<Pet> findAll();
}
