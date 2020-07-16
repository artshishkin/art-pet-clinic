package com.artarkatesoft.artpetclinic.controllers;

import com.artarkatesoft.artpetclinic.model.Owner;
import com.artarkatesoft.artpetclinic.model.Pet;
import com.artarkatesoft.artpetclinic.model.PetType;
import com.artarkatesoft.artpetclinic.services.OwnerService;
import com.artarkatesoft.artpetclinic.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

    @InjectMocks
    PetController petController;

    MockMvc mockMvc;

    Owner owner;
    Set<PetType> petTypes;

    @Captor
    ArgumentCaptor<Owner> ownerCaptor;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();
        petTypes = new HashSet<>();
        petTypes.add(PetType.builder().id(1L).name("Dog").build());
        petTypes.add(PetType.builder().id(2L).name("Cat").build());
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
        Pet pet = Pet.builder()
                .name("Kuzya")
                .petType(petTypes.iterator().next())
                .birthDate(LocalDate.now())
                .owner(owner)
                .build();
        //when
        mockMvc
                .perform(
                        post("/owners/{ownerId}/pets/new", ownerId)
                                .param("name", pet.getName())
//                                .param("birthDate", pet.getBirthDate().toString())
                                .param("petType.id", pet.getPetType().getId().toString())
                                .param("petType.name", pet.getPetType().getName())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlTemplate("/owners/{ownerId}", ownerId));

        //then
        then(ownerService).should().findById(eq(ownerId));
        then(ownerService).should().save(ownerCaptor.capture());
        Owner captorValue = ownerCaptor.getValue();
        assertThat(captorValue.getId()).isEqualTo(ownerId);
        assertThat(captorValue.getPets())
                .hasOnlyOneElementSatisfying(
                        p -> assertAll(
                                () -> assertThat(p.getName()).isEqualTo(pet.getName()),
                                () -> assertThat(p.getPetType()).isEqualToComparingFieldByField(pet.getPetType()),
                                () -> assertThat(p.getOwner().getId()).isEqualTo(pet.getOwner().getId())
                        ));
    }


}
