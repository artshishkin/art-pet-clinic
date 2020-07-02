package com.artarkatesoft.artpetclinic.services.map;

import com.artarkatesoft.artpetclinic.model.Pet;
import com.artarkatesoft.artpetclinic.services.PetService;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class PetServiceMap extends AbstractMapService<Pet, Long> implements PetService {
    @Override
    public Pet save(Pet pet) {
        Long id = pet.getId() != null ?
                pet.getId() :
                map.keySet().stream().max(Comparator.naturalOrder()).orElse(1L);
        return this.save(id, pet);
    }
}
