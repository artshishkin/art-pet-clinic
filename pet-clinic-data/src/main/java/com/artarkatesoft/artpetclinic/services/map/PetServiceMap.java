package com.artarkatesoft.artpetclinic.services.map;

import com.artarkatesoft.artpetclinic.model.Pet;
import com.artarkatesoft.artpetclinic.services.PetService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Objects;

@Service
public class PetServiceMap extends AbstractMapService<Pet, Long> implements PetService {
    @Override
    public Pet save(Pet pet) {
        Long id = pet.getId() != null ?
                pet.getId() :
                (
                        map.keySet().stream()
                                .filter(Objects::nonNull)
                                .max(Comparator.naturalOrder())
                                .orElse(0L) + 1L
                );
        pet.setId(id);
        return this.save(id, pet);
    }
}
