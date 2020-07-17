package com.artarkatesoft.artpetclinic.controllers;

import com.artarkatesoft.artpetclinic.model.Pet;
import com.artarkatesoft.artpetclinic.model.Visit;
import com.artarkatesoft.artpetclinic.services.PetService;
import com.artarkatesoft.artpetclinic.services.VisitService;
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
@DisplayName("Test Visit Controller without Spring context")
class VisitControllerTest {

    MockMvc mockMvc;

    @Mock
    VisitService visitService;
    @Mock
    PetService petService;

    @InjectMocks
    VisitController visitController;

    @Captor
    ArgumentCaptor<Visit> visitCaptor;

    private Pet pet;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders.standaloneSetup(visitController).build();

        pet = Pet.builder().build();
        pet.setId(100L);
        given(petService.findById(anyLong())).willReturn(pet);
    }

    @Test
    @DisplayName("GETting new visit form should be ok")
    void initNewVisitForm() throws Exception {
        //given
        Long petId = pet.getId();
        //when
        mockMvc.perform(get("/owners/*/pets/{petId}/visits/new", petId))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdateVisitForm"))
                .andExpect(model().attributeExists("visit"));
        //then
        then(petService).should().findById(eq(petId));
    }

    @Test
    @DisplayName("POSTing new visit should save visit and redirect to Owners info page")
    void processNewVisitForm() throws Exception {
        //given
        Long petId = pet.getId();
        Long ownerId = 1L;
        final String VISIT_DESCRIPTION = "Visit description";
        //when
        mockMvc.perform(
                post("/owners/{ownerId}/pets/{petId}/visits/new", ownerId, petId)
                        .param("description", VISIT_DESCRIPTION))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlTemplate("/owners/{ownerId}", ownerId));
        //then
        then(petService).should().findById(eq(petId));
        then(visitService).should().save(visitCaptor.capture());
        Visit savedVisit = visitCaptor.getValue();
        assertAll(
                () -> assertThat(savedVisit.getDescription()).isEqualTo(VISIT_DESCRIPTION),
                () -> assertThat(savedVisit.getPet().getId()).isEqualTo(petId)
        );
    }
}
