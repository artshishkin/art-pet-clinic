package com.artarkatesoft.artpetclinic.services.springdatajpa;

import com.artarkatesoft.artpetclinic.model.Owner;
import com.artarkatesoft.artpetclinic.repositories.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    @InjectMocks
    OwnerSDJpaService service;

    @Mock
    OwnerRepository repository;

    Owner owner;

    @BeforeEach
    void setUp() {
        owner = Owner.builder()
                .address("addr1")
                .city("city1")
                .firstName("Art")
                .lastName("Shyshkin")
                .telephone("123123")
                .build();
    }

    @Test
    void findByIdPresent() {
        //given
        given(repository.findById(anyLong())).willReturn(Optional.of(owner));
        //when
        Owner foundOwner = service.findById(anyLong());
        //then
        then(repository).should().findById(anyLong());
        assertEquals(owner, foundOwner);
    }

    @Test
    void findByIdAbsent() {
        //given
        given(repository.findById(anyLong())).willReturn(Optional.empty());
        //when
        Owner foundOwner = service.findById(anyLong());
        //then
        then(repository).should().findById(anyLong());
        assertNull(foundOwner);
    }

    @Test
    void save() {
        //when
        service.save(owner);
        //then
        then(repository).should().save(eq(owner));
    }

    @Test
    void findAll() {
        //given
        given(repository.findAll()).willReturn(Collections.singleton(owner));
        //when
        Set<Owner> allOwners = service.findAll();
        //then
        then(repository).should(times(1)).findAll();
        then(repository).shouldHaveNoMoreInteractions();
        assertThat(allOwners).hasSize(1);
        assertThat(allOwners).contains(owner);
    }

    @Test
    void delete() {
        //when
        service.delete(owner);
        //then
        then(repository).should().delete(eq(owner));
        then(repository).shouldHaveNoMoreInteractions();
    }

    @Test
    void deleteById() {
        //when
        service.deleteById(anyLong());

        //then
        then(repository).should().deleteById(anyLong());
        then(repository).shouldHaveNoMoreInteractions();
    }

    @Test
    void findByLastName() {
        //given
        given(repository.findOneByLastName(anyString())).willReturn(owner);
        final String lastName = owner.getLastName();

        //when
        Owner foundOwner = service.findByLastName(lastName);

        //then
        then(repository).should().findOneByLastName(eq(lastName));
        then(repository).shouldHaveNoMoreInteractions();
        assertEquals(owner.getLastName(), foundOwner.getLastName());
    }

    @Test
    void findAllByLastNameLike() {
        //given
        String lastName = "foo";
        //when
        service.findAllByLastNameLike(lastName);
        //then
        then(repository).should().findAllByLastNameContains(eq(lastName));
    }
}
