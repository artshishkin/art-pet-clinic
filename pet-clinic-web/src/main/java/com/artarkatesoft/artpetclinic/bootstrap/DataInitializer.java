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

        PetType dog = PetType.builder().name("dog").build();

        PetType cat = PetType.builder().name("cat").build();

        Owner owner;
        owner = Owner.builder().firstName("Art")
                .lastName("Shyshkin")
                .address("Address 1")
                .city("City 1")
                .telephone("1234567")
                .build();


        Pet pet;
        pet = Pet.builder()
                .name("Doggy 1")
                .birthDate(LocalDate.now())
                .petType(dog)
                .owner(owner)
                .build();

        owner.getPets().add(pet);

        pet = Pet.builder()
                .name("Kitty 1")
                .birthDate(LocalDate.now())
                .petType(cat)
                .owner(owner)
                .build();

        owner.getPets().add(pet);

        owner = ownerService.save(owner);

        Visit visit;
        visit = Visit.builder()
                .pet(owner.getPets().stream().findFirst().get())
                .date(LocalDate.now())
                .description("Skin is soft")
                .build();
        visitService.save(visit);

        owner = Owner.builder()
                .firstName("Nazar")
                .lastName("Shyshkin")
                .address("Address 2")
                .address("City 2")
                .telephone("98765544")
                .build();

        pet = Pet.builder()
                .name("Doggy 2")
                .birthDate(LocalDate.now())
                .petType(dog)
                .owner(owner)
                .build();
        owner.getPets().add(pet);

        owner = ownerService.save(owner);

        visit = Visit.builder()
                .pet(owner.getPets().stream().findFirst().get())
                .date(LocalDate.now())
                .description("Some headache")
                .build();
        visitService.save(visit);

    }

    private void bootstrapVets() {
//        INSERT INTO specialties VALUES (1, 'radiology');
//        INSERT INTO specialties VALUES (2, 'surgery');
//        INSERT INTO specialties VALUES (3, 'dentistry');

        Specialty radiology = Specialty.builder().description("radiology").build();

        Specialty surgery = Specialty.builder().description("surgery").build();

        Specialty dentistry = Specialty.builder().description("dentistry").build();

        Vet vet = Vet.builder()
                .firstName("Kate")
                .lastName("Dobryden")
                .build();
        vet.getSpecialties().add(radiology);

        vetService.save(vet);
        vet.getSpecialties().add(dentistry);
        vetService.save(vet);

        vet = Vet.builder()
                .firstName("Arina")
                .lastName("Shyshkina")
                .build();

        vet.getSpecialties().add(surgery);
        vetService.save(vet);
    }
}
