package com.artarkatesoft.artpetclinic.controllers;

import com.artarkatesoft.artpetclinic.model.Specialty;
import com.artarkatesoft.artpetclinic.model.Vet;
import com.artarkatesoft.artpetclinic.services.VetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.ResultMatcher.matchAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VetControllerTest {

    MockMvc mockMvc;

    @Mock
    VetService vetService;

    @InjectMocks
    VetController vetController;

    private Set<Vet> defaultVets = fakeVets();

    private Set<Vet> fakeVets() {
        return LongStream.rangeClosed(1, 2)
                .mapToObj(this::fakeVet)
                .collect(Collectors.toSet());
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(vetController).build();
    }

    private Vet fakeVet(Long vetId) {
        Vet vet = new Vet();
        vet.setId(vetId);
        vet.setFirstName("firstName" + vetId);
        vet.setLastName("lastName" + vetId);

        LongStream.rangeClosed(1, 3)
                .mapToObj(specId -> fakeSpecialty(vetId, specId))
                .forEach(vet.getSpecialties()::add);
        return vet;
    }

    private Specialty fakeSpecialty(Long vetId, Long specId) {
        Specialty specialty = new Specialty("spec " + vetId + " " + specId);
        specialty.setId(100 * vetId + specId);
        return specialty;
    }

    @Test
    void getVetsJson() throws Exception {
        //given
        given(vetService.findAll()).willReturn(defaultVets);
        Vet firstVet = defaultVets.iterator().next();
        int specSize = firstVet.getSpecialties().size();

        //when
        mockMvc.perform(get("/api/vets"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(matchAll(
                        jsonPath("$.[0].id").value(firstVet.getId()),
                        jsonPath("$.[0].firstName").value(firstVet.getFirstName()),
                        jsonPath("$.[0].lastName").value(firstVet.getLastName()),
                        jsonPath("$.[0].specialties.[0].description").value(firstVet.getSpecialties().iterator().next().getDescription()),
                        jsonPath("$.[0].specialties").value(hasSize(specSize)))
                );
        //then
        then(vetService).should().findAll();
    }
}
