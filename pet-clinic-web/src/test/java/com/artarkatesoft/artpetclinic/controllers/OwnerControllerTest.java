package com.artarkatesoft.artpetclinic.controllers;

import com.artarkatesoft.artpetclinic.model.Owner;
import com.artarkatesoft.artpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.ResultMatcher.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    private MockMvc mockMvc;
    //@WebMvcTest - 1063ms
    //mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build(); - 3032ms

    @Mock
    OwnerService ownerService;

    @InjectMocks
    OwnerController ownerController;

    private Owner owner;

    @BeforeEach
    void setUp() {
        owner = Owner.builder()
                .firstName("Art")
                .lastName("Shyshkin")
                .city("City1")
                .address("Address1")
                .telephone("123")
                .build();
        owner.setId(1L);

        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
    }

    @Test
    void list() throws Exception {
        given(ownerService.findAll()).willReturn(Collections.singleton(owner));
        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/index"))
                .andExpect(model().attributeExists("owners"))
                .andExpect(model().attribute("owners", hasSize(1)));
        then(ownerService).should().findAll();
    }

    @Test
    void find() throws Exception {
        mockMvc.perform(get("/owners/find"))
                .andExpect(matchAll(
                        status().isOk(),
                        view().name("not_implemented")
                ));
        then(ownerService).shouldHaveNoInteractions();
    }
}