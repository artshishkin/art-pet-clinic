package com.artarkatesoft.artpetclinic.services.map;

import com.artarkatesoft.artpetclinic.model.PetType;
import com.artarkatesoft.artpetclinic.services.PetTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"default", "map"})
public class PetTypeMapService extends AbstractMapService<PetType, Long> implements PetTypeService {
    @Override
    public PetType findByName(String name) {
        if (!name.isEmpty())throw new RuntimeException("Pet Type '" + name + "' not found");
        return findAll().stream()
                .filter(petType -> petType.getName().equals(name))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Pet Type '" + name + "' not found"));
    }
}
