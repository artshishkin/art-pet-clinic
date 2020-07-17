package com.artarkatesoft.artpetclinic.controllers;

import com.artarkatesoft.artpetclinic.model.Pet;
import com.artarkatesoft.artpetclinic.model.Visit;
import com.artarkatesoft.artpetclinic.services.PetService;
import com.artarkatesoft.artpetclinic.services.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VisitController.class)
@DisplayName("Test Visit Controller with Spring @WebMvcTest")
class VisitControllerSpringTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    VisitService visitService;
    @MockBean
    PetService petService;

    @Captor
    ArgumentCaptor<Visit> visitCaptor;

    private Pet pet;

    @BeforeEach
    void setUp() {
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
                ()->assertThat(savedVisit.getDescription()).isEqualTo(VISIT_DESCRIPTION),
                ()->assertThat(savedVisit.getPet().getId()).isEqualTo(petId)
        );
    }
}
