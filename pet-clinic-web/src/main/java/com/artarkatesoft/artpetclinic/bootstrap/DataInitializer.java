package com.artarkatesoft.artpetclinic.bootstrap;

import com.artarkatesoft.artpetclinic.model.Owner;
import com.artarkatesoft.artpetclinic.model.Pet;
import com.artarkatesoft.artpetclinic.model.PetType;
import com.artarkatesoft.artpetclinic.model.Vet;
import com.artarkatesoft.artpetclinic.services.OwnerService;
import com.artarkatesoft.artpetclinic.services.VetService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;

    @Override
    public void run(String... args) throws Exception {

        PetType dog = new PetType();
        dog.setName("dog");

        PetType cat = new PetType();
        cat.setName("cat");

        Owner owner;
        owner = new Owner();
        owner.setFirstName("Art");
        owner.setLastName("Shyshkin");
        owner.setAddress("Address 1");
        owner.setAddress("City 1");
        owner.setTelephone("1234567");

        Pet pet;
        pet = new Pet();
        pet.setName("Doggy 1");
        pet.setBirthDate(LocalDate.now());
        pet.setPetType(dog);
        pet.setOwner(owner);

        owner.getPets().add(pet);

        pet = new Pet();
        pet.setName("Kitty 1");
        pet.setBirthDate(LocalDate.now());
        pet.setPetType(cat);
        pet.setOwner(owner);

        owner.getPets().add(pet);

        ownerService.save(owner);

        Vet vet = new Vet();
        vet.setFirstName("Kate");
        vet.setLastName("Dobryden");
        vetService.save(vet);

        owner = new Owner();
        owner.setFirstName("Nazar");
        owner.setLastName("Shyshkin");
        owner.setId(111L);
        owner.setAddress("Address 2");
        owner.setAddress("City 2");
        owner.setTelephone("98765544");

        pet = new Pet();
        pet.setName("Doggy 2");
        pet.setBirthDate(LocalDate.now());
        pet.setPetType(dog);
        pet.setOwner(owner);

        owner.getPets().add(pet);

        ownerService.save(owner);

        vet = new Vet();
        vet.setFirstName("Arina");
        vet.setLastName("Shyshkina");
        vet.setId(222L);
        vetService.save(vet);

        System.out.println("--------All Owners------");
        System.out.println(ownerService.findAll());
        System.out.println("--------All Vets------");
        System.out.println(vetService.findAll());
    }
}
