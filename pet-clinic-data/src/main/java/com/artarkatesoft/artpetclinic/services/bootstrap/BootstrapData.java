package com.artarkatesoft.artpetclinic.services.bootstrap;

import com.artarkatesoft.artpetclinic.model.Owner;
import com.artarkatesoft.artpetclinic.model.Pet;
import com.artarkatesoft.artpetclinic.model.PetType;
import com.artarkatesoft.artpetclinic.model.Vet;
import com.artarkatesoft.artpetclinic.services.OwnerService;
import com.artarkatesoft.artpetclinic.services.PetService;
import com.artarkatesoft.artpetclinic.services.VetService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final OwnerService ownerService;
    private final PetService petService;
    private final VetService vetService;

    @Override
    public void run(String... args) throws Exception {
        Owner owner = new Owner();
        owner.setFirstName("Art");
        owner.setLastName("Shyshkin");
        owner = ownerService.save(owner);

        PetType dog = new PetType();
        dog.setName("dog");
        PetType cat = new PetType();
        cat.setName("cat");
        
        Pet pet;
        pet = new Pet();
        pet.setBirthDate(LocalDate.now());
        pet.setPetType(dog);
        pet.setOwner(owner);
        petService.save(pet);

        pet = new Pet();
        pet.setBirthDate(LocalDate.now());
        pet.setPetType(cat);
        pet.setOwner(owner);
        petService.save(pet);

        Vet vet = new Vet();
        vet.setFirstName("Kate");
        vet.setLastName("Dobryden");
        vetService.save(vet);

        System.out.println("--------All Owners------");
        System.out.println(ownerService.findAll());
        System.out.println("--------All Vets------");
        System.out.println(vetService.findAll());
        System.out.println("--------All Pets------");
        System.out.println(petService.findAll());



    }
}
