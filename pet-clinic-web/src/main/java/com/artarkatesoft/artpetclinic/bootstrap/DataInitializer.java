package com.artarkatesoft.artpetclinic.bootstrap;

import com.artarkatesoft.artpetclinic.model.*;
import com.artarkatesoft.artpetclinic.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final VisitService visitService;
    private final PetTypeService petTypeService;
    private final SpecialtyService specialtyService;

    @Override
    public void run(String... args) throws Exception {

        boolean dataLoaded = !ownerService.findAll().isEmpty();
        if (!dataLoaded) {
            loadData();
        }

    }

    private void loadData() {
        bootstrapOwners();
        bootstrapVets();
        System.out.println("--------All Owners------");
        System.out.println(ownerService.findAll());
        System.out.println("--------All Vets------");
        System.out.println(vetService.findAll());
        System.out.println("--------All Visits------");
        System.out.println(visitService.findAll());
    }

    private void bootstrapOwners() {

        PetType dog = new PetType();
        dog.setName("dog");
//        dog = petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("cat");
//        cat = petTypeService.save(cat);

        Owner owner;
        owner = Owner.builder()
                .firstName("Art")
                .lastName("Shyshkin")
                .address("Address 1")
                .city("City 1")
                .telephone("1234567")
                .build();

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

        owner = ownerService.save(owner);

        Visit visit;
        visit = new Visit();
        Pet pet1 = owner.getPets().stream().findFirst().get();
        visit.setPet(pet1);
        visit.setDate(LocalDate.now());
        visit.setDescription("Skin is soft");
        visitService.save(visit);

        owner = Owner.builder()
                .firstName("Nazar")
                .lastName("Shyshkin")
                .address("Address 2")
                .city("City 2")
                .telephone("98765544")
                .build();

        pet = new Pet();
        pet.setName("Doggy 2");
        pet.setBirthDate(LocalDate.now());
        pet.setPetType(dog);
        pet.setOwner(owner);

        owner.getPets().add(pet);

        owner = ownerService.save(owner);

        visit = new Visit();
        Pet pet2 = owner.getPets().stream().findFirst().get();
        visit.setPet(pet2);
        visit.setDate(LocalDate.now());
        visit.setDescription("Some headache");
        visitService.save(visit);

    }

    private void bootstrapVets() {
//        INSERT INTO specialties VALUES (1, 'radiology');
//        INSERT INTO specialties VALUES (2, 'surgery');
//        INSERT INTO specialties VALUES (3, 'dentistry');

        Specialty radiology = new Specialty();
        radiology.setDescription("radiology");
//        radiology = specialtyService.save(radiology);

        Specialty surgery = new Specialty();
        surgery.setDescription("surgery");
//        surgery = specialtyService.save(surgery);

        Specialty dentistry = new Specialty();
        dentistry.setDescription("dentistry");
//        dentistry = specialtyService.save(dentistry);

        Vet vet = new Vet();
        vet.setFirstName("Kate");
        vet.setLastName("Dobryden");
        vet.getSpecialties().add(radiology);
        vetService.save(vet);
        vet.getSpecialties().add(dentistry);
        vetService.save(vet);

        vet = new Vet();
        vet.setFirstName("Arina");
        vet.setLastName("Shyshkina");
        vet.setId(222L);
        vet.getSpecialties().add(surgery);
        vetService.save(vet);
    }
}
