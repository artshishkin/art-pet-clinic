package com.artarkatesoft.artpetclinic.services.map;

import com.artarkatesoft.artpetclinic.model.Owner;
import com.artarkatesoft.artpetclinic.model.Pet;
import com.artarkatesoft.artpetclinic.model.PetType;
import com.artarkatesoft.artpetclinic.services.OwnerService;
import com.artarkatesoft.artpetclinic.services.PetService;
import com.artarkatesoft.artpetclinic.services.PetTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnerMapService extends AbstractMapService<Owner, Long> implements OwnerService {

    private final PetService petService;
    private final PetTypeService petTypeService;

    @Override
    public Owner findByLastName(String lastName) {
        return this.findAll().stream().filter(owner -> owner.getLastName().equals(lastName)).findFirst().orElse(null);
    }

    @Override
    public Owner save(Owner owner) {

        if (owner == null) return null;

        owner.getPets().forEach(pet -> {
            if (pet == null) throw new RuntimeException("Pet shold not be null");
            PetType petType = pet.getPetType();
            if (petType == null) throw new RuntimeException("PetType should not be null");
            if (petType.getId() == null) {
                PetType savedPetType = petTypeService.save(petType);
                petType.setId(savedPetType.getId());
            }
            if (pet.getId() == null) {
                Pet savedPet = petService.save(pet);
                pet.setId(savedPet.getId());
            }
        });
        return super.save(owner);
    }
}