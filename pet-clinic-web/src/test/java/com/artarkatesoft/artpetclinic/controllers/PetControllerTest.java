package com.artarkatesoft.artpetclinic.controllers;

import com.artarkatesoft.artpetclinic.formatters.PetTypeFormatter;
import com.artarkatesoft.artpetclinic.model.Owner;
import com.artarkatesoft.artpetclinic.model.Pet;
import com.artarkatesoft.artpetclinic.model.PetType;
import com.artarkatesoft.artpetclinic.services.OwnerService;
import com.artarkatesoft.artpetclinic.services.PetService;
import com.artarkatesoft.artpetclinic.services.PetTypeService;
import lombok.var;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testing PetController with JUnit and Mockito mockMvc")
class PetControllerTest {

    @Mock
    PetTypeService petTypeService;

    @Mock
    OwnerService ownerService;

    @Mock
    PetService petService;

    @InjectMocks
    PetController petController;

    MockMvc mockMvc;

    Owner owner;
    Set<PetType> petTypes;

    @Captor
    ArgumentCaptor<Pet> petCaptor;

    @BeforeEach
    void setUp() {

        var conversionService = new DefaultFormattingConversionService();
        conversionService.addFormatterForFieldType(PetType.class, new PetTypeFormatter(petTypeService));

        mockMvc = MockMvcBuilders
                .standaloneSetup(petController)
                .setConversionService(conversionService)
                .build();
        petTypes = new HashSet<>();
        PetType dog = PetType.builder().id(1L).name("Dog").build();
        petTypes.add(dog);
        PetType cat = PetType.builder().id(2L).name("Cat").build();
        petTypes.add(cat);
        owner = Owner.builder().build();
        owner.setId(1L);

        given(ownerService.findById(anyLong())).willReturn(owner);
        given(petTypeService.findAll()).willReturn(petTypes);
    }

    @Test
    @DisplayName("GETting form to create new pet")
    void initCreationForm() throws Exception {
        //given
        Long ownerId = owner.getId();
        //when
        mockMvc.perform(get("/owners/{ownerId}/pets/new", ownerId))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner", "pet"))
                .andExpect(view().name(PetController.VIEWS_PETS_CREATE_OR_UPDATE_FORM));
        //then
        then(ownerService).should().findById(eq(ownerId));
    }

    @Test
    @DisplayName("POSTing form to create new pet")
    void processCreationForm() throws Exception {
        //given
        Long ownerId = 1L;
        Owner owner = Owner.builder().build();
        owner.setId(ownerId);
        given(ownerService.findById(anyLong())).willReturn(owner);

        PetType dog = PetType.builder().id(1L).name("Dog").build();
        given(petTypeService.findByName(eq("Dog"))).willReturn(dog);

        Pet pet = Pet.builder()
                .name("Kuzya")
                .petType(dog)
                .birthDate(LocalDate.now())
                .owner(owner)
                .build();

        //when
        mockMvc
                .perform(
                        post("/owners/{ownerId}/pets/new", ownerId)
                                .param("name", pet.getName())
//                                .param("birthDate", pet.getBirthDate().toString())
                                .param("petType", pet.getPetType().getName())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlTemplate("/owners/{ownerId}", ownerId));

        //then
        then(ownerService).should().findById(eq(ownerId));
        then(petService).should().save(petCaptor.capture());
        Pet captorValue = petCaptor.getValue();
        assertThat(captorValue.getOwner().getId()).isEqualTo(ownerId);
        assertAll(
                () -> assertThat(captorValue.getName()).isEqualTo(pet.getName()),
                () -> assertThat(captorValue.getPetType()).isEqualToComparingFieldByField(pet.getPetType()),
                () -> assertThat(captorValue.getOwner().getId()).isEqualTo(pet.getOwner().getId())
        );
    }

    @Test
    @DisplayName("GETting form to Update new pet")
    void initUpdateForm() throws Exception {
        //given
        Long ownerId = owner.getId();
        PetType petType = petTypes.iterator().next();
        Pet pet = Pet.builder().petType(petType).name("Old").owner(owner).build();
        Long petId = 3L;
        pet.setId(petId);
        given(petService.findById(anyLong())).willReturn(pet);
        //when
        mockMvc.perform(get("/owners/{ownerId}/pets/{petId}/edit", ownerId, petId))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner", "pet"))
                .andExpect(view().name(PetController.VIEWS_PETS_CREATE_OR_UPDATE_FORM));
        //then
        then(ownerService).should().findById(eq(ownerId));
    }

    @Test
    @DisplayName("POSTing form to Update a pet")
    void processUpdateForm() throws Exception {
        //given
        Long ownerId = 1L;
        Owner owner = Owner.builder().build();
        owner.setId(ownerId);
        given(ownerService.findById(anyLong())).willReturn(owner);

        PetType dog = PetType.builder().id(1L).name("Dog").build();
        given(petTypeService.findByName(eq("Dog"))).willReturn(dog);

        String petBirthDateString = "2016-07-03";
        LocalDate birthDate = LocalDate.parse(petBirthDateString);

        Pet pet = Pet.builder()
                .name("Kuzya")
                .petType(dog)
                .birthDate(birthDate)
                .owner(owner)
                .build();
        Long petId = 3L;
        pet.setId(petId);
        //when
        mockMvc
                .perform(
                        post("/owners/{ownerId}/pets/{petId}/edit", ownerId, petId)
                                .param("name", pet.getName())
                                .param("id", pet.getId().toString())
                                .param("birthDate", petBirthDateString)
                                .param("petType", pet.getPetType().getName())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlTemplate("/owners/{ownerId}", ownerId));

        //then
        then(ownerService).should().findById(eq(ownerId));
        then(petService).should().save(petCaptor.capture());
        Pet savedPet = petCaptor.getValue();
        assertThat(savedPet.getId()).isEqualTo(petId);
        assertAll(
                () -> assertThat(savedPet.getName()).isEqualTo(pet.getName()),
                () -> assertThat(savedPet.getBirthDate()).isEqualTo(birthDate),
                () -> assertThat(savedPet.getPetType()).isEqualToComparingFieldByField(pet.getPetType()),
                () -> assertThat(savedPet.getOwner().getId()).isEqualTo(pet.getOwner().getId())
        );
    }

}
