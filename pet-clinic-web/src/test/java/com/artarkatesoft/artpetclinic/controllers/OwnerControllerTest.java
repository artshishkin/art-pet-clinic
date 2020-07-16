package com.artarkatesoft.artpetclinic.controllers;

import com.artarkatesoft.artpetclinic.model.Owner;
import com.artarkatesoft.artpetclinic.services.OwnerService;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.ResultMatcher.matchAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Captor
    ArgumentCaptor<Owner> ownerCaptor;

    private Owner defaultOwner;
    private List<Owner> defaultOwnerList;

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

        Owner secondDefaultOwner = Owner.builder()
                .firstName("Arina")
                .lastName("Shyshkina")
                .city("City1")
                .address("Address1")
                .telephone("321")
                .build();
        secondDefaultOwner.setId(2L);
        defaultOwnerList = new ArrayList<>();
        defaultOwnerList.add(defaultOwner);
        defaultOwnerList.add(secondDefaultOwner);

        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
    }

    @Test
    void find() throws Exception {
        //when
        mockMvc.perform(get("/owners/find"))
                .andExpect(matchAll(
                        status().isOk(),
                        view().name("owners/findOwners"),
                        model().attributeExists("owner")
                ));
        //then
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

    @Nested
    @DisplayName("processing find form")
    class ProcessFindForm {

        private String lastNameLike;

        @BeforeEach
        void setUp() {
            lastNameLike = "shkina";
        }

        @Test
        @DisplayName("when there is no query param for lastName should return ALL owners")
        void testProcessFindForm_requestEmptyOwner() throws Exception {
            //given
            given(ownerService.findAllByLastNameLike(anyString())).willReturn(defaultOwnerList);
            //when
            mockMvc.perform(get("/owners"))
                    //then
                    .andExpect(status().isOk())
                    .andExpect(view().name("owners/ownersList"))
                    .andExpect(model().attributeExists("selections"))
                    .andExpect(model().attribute("selections", Matchers.iterableWithSize(defaultOwnerList.size())));
            then(ownerService).should().findAllByLastNameLike(eq(""));
        }

        @Test
        @DisplayName("when results NOT found")
        void testProcessFindForm_resultNone() throws Exception {
            //given
            given(ownerService.findAllByLastNameLike(anyString())).willReturn(Collections.emptyList());
            //when
            mockMvc
                    .perform(
                            get("/owners")
                                    .param("lastName", lastNameLike))
                    //then
                    .andExpect(status().isOk())
                    .andExpect(view().name("owners/findOwners"))
                    .andExpect(model().hasErrors())
                    .andExpect(model().attributeHasFieldErrorCode("owner", "lastName", "notFound"));
            then(ownerService).should().findAllByLastNameLike(eq(lastNameLike));
        }

        @Test
        @DisplayName("when found ONE result")
        void testProcessFindForm_resultOne() throws Exception {
            //given
            given(ownerService.findAllByLastNameLike(anyString())).willReturn(Collections.singletonList(defaultOwner));
            //when
            mockMvc.perform(get("/owners").param("lastName", lastNameLike))
                    //then
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrlTemplate("/owners/{ownerId}", defaultOwner.getId()));
            //then
            then(ownerService).should().findAllByLastNameLike(eq(lastNameLike));
        }

        @Test
        @DisplayName("when found MANY results")
        void testProcessFindForm_resultMany() throws Exception {
            //given
            given(ownerService.findAllByLastNameLike(anyString())).willReturn(defaultOwnerList);
            //when
            mockMvc
                    .perform(
                            get("/owners")
                                    .param("lastName", lastNameLike))
                    //then
                    .andExpect(status().isOk())
                    .andExpect(view().name("owners/ownersList"))
                    .andExpect(model().attributeExists("selections"))
                    .andExpect(model().attribute("selections", Matchers.iterableWithSize(defaultOwnerList.size())));
            //then
            then(ownerService).should().findAllByLastNameLike(eq(lastNameLike));
        }
    }

    @Test
    void initCreationForm() throws Exception {
        //when
        mockMvc.perform(get("/owners/new"))
                //then
                .andExpect(status().isOk())
                .andExpect(view().name(OwnerController.CREATE_OR_UPDATE_OWNER_FORM))
                .andExpect(model().attributeExists("owner"));
    }

    @Test
    void processCreationForm() throws Exception {
        //given
        final Long ownerId = defaultOwner.getId();
        given(ownerService.save(any(Owner.class)))
                .willAnswer((Answer<Owner>) invocation -> {
                    Owner owner = invocation.getArgument(0, Owner.class);
                    owner.setId(ownerId);
                    return null;
                });
        //when
        mockMvc
                .perform(
                        post("/owners/new")
                                .param("firstName", defaultOwner.getFirstName())
                                .param("lastName", defaultOwner.getLastName())
                                .param("city", defaultOwner.getCity())
                                .param("address", defaultOwner.getAddress())
                                .param("telephone", defaultOwner.getTelephone())
                )
                //then
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlTemplate("/owners/{ownerId}", ownerId));
        then(ownerService).should().save(ownerCaptor.capture());
        Owner savedOwner = ownerCaptor.getValue();
        assertThat(savedOwner).isEqualToIgnoringNullFields(defaultOwner);

    }


}
