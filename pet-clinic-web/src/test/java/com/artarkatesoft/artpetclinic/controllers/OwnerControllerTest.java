package com.artarkatesoft.artpetclinic.controllers;

import com.artarkatesoft.artpetclinic.model.Owner;
import com.artarkatesoft.artpetclinic.services.OwnerService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.ResultMatcher.matchAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    private Owner defaultOwner;

    @BeforeEach
    void setUp() {
        defaultOwner = Owner.builder()
                .firstName("Art")
                .lastName("Shyshkin")
                .city("City1")
                .address("Address1")
                .telephone("123")
                .build();
        defaultOwner.setId(1L);

        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
    }

    @Test
    void list() throws Exception {
        given(ownerService.findAll()).willReturn(Collections.singleton(defaultOwner));
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

    @Test
    void testShowOwnerDetails() throws Exception {
        //given
        Long ownerId = defaultOwner.getId();
        given(ownerService.findById(anyLong())).willReturn(defaultOwner);

        //when
        mockMvc.perform(get("/owners/{ownerId}", ownerId))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownerDetails"))
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attribute("owner", CoreMatchers.notNullValue()))
                .andExpect(model().attribute("owner", hasProperty("id", is(ownerId))))
        ;
        //then
        then(ownerService).should().findById(eq(ownerId));
    }
}
