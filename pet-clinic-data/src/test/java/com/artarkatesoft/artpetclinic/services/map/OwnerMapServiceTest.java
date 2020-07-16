package com.artarkatesoft.artpetclinic.services.map;

import com.artarkatesoft.artpetclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OwnerMapServiceTest {

    OwnerMapService ownerMapService;
    //with Mockito 4328ms
    //without Mockito 676ms

    @BeforeEach
    void setUp() {
        ownerMapService = new OwnerMapService(new PetMapService(), new PetTypeMapService());
        Owner owner = Owner.builder()
                .address("addr1")
                .city("city1")
                .firstName("Art")
                .lastName("Shyshkin")
                .telephone("123123")
                .build();
        owner.setId(1L);
        ownerMapService.map.put(owner.getId(), owner);
        owner = Owner.builder()
                .address("addr2")
                .city("city2")
                .firstName("Kate")
                .lastName("Shyshkina")
                .telephone("456456")
                .build();
        owner.setId(2L);
        ownerMapService.map.put(owner.getId(), owner);
        owner = Owner.builder()
                .address("addr3")
                .city("city3")
                .firstName("Nazar")
                .lastName("Shyshkin")
                .build();
        owner.setId(3L);
        ownerMapService.map.put(owner.getId(), owner);
    }

    @Test
    void findAll() {
        Set<Owner> allOwners = ownerMapService.findAll();
        int dbSize = ownerMapService.map.size();
        assertAll(
                () -> assertThat(allOwners).isNotNull(),
                () -> assertThat(allOwners).hasSize(dbSize)
        );
    }

    @Test
    void findById() {
        Owner owner = ownerMapService.findById(1L);
        assertThat(owner.getId()).isEqualTo(1L);
    }

    @Test
    void saveNullId() {
        Owner arina = Owner.builder()
                .firstName("Arina")
                .lastName("Shyshkina")
                .build();
        Owner savedArina = ownerMapService.save(arina);
        assertAll(
                () -> assertThat(savedArina.getFirstName()).isEqualTo(arina.getFirstName()),
                () -> assertThat(savedArina.getLastName()).isEqualTo(arina.getLastName()),
                () -> assertThat(savedArina.getId()).isEqualTo(ownerMapService.map.size())
        );
    }

    @Test
    void saveExistingId() {
        Owner fooBar = Owner.builder()
                .firstName("Foo")
                .lastName("Bar")
                .build();
        fooBar.setId(111L);
        Owner savedFooBar = ownerMapService.save(fooBar);
        assertAll(
                () -> assertThat(savedFooBar.getFirstName()).isEqualTo(fooBar.getFirstName()),
                () -> assertThat(savedFooBar.getLastName()).isEqualTo(fooBar.getLastName()),
                () -> assertThat(savedFooBar.getId()).isEqualTo(fooBar.getId())
        );
    }

    @Test
    void saveNull() {
        assertThrows(NullPointerException.class, () -> ownerMapService.save(null));
    }

    @Test
    void delete() {
        Owner owner = ownerMapService.map.get(1L);
        int initialSize = ownerMapService.map.size();
        ownerMapService.delete(owner);
        int finalSize = ownerMapService.map.size();
        assertAll(
                () -> assertFalse(ownerMapService.map.containsKey(1L)),
                () -> assertEquals(initialSize - 1, finalSize)
        );
    }

    @Test
    void deleteById() {
        int initialSize = ownerMapService.map.size();
        ownerMapService.deleteById(2L);
        int finalSize = ownerMapService.map.size();
        assertAll(
                () -> assertFalse(ownerMapService.map.containsKey(2L)),
                () -> assertEquals(initialSize - 1, finalSize)
        );
    }

    @Test
    void findByLastNameExisting() {
        final String lastName = "Shyshkin";
        Owner owner = ownerMapService.findByLastName(lastName);
        assertThat(owner.getLastName()).isEqualTo(lastName);
    }

    @Test
    void findByLastNameAbsent() {
        final String lastName = "Hoola";
        Owner owner = ownerMapService.findByLastName(lastName);
        assertNull(owner);
    }

    @Test
    void findAllByLastNameLikeOneMatch() {
        String lastName = "kina";
        List<Owner> owners = ownerMapService.findAllByLastNameLike(lastName);
        assertThat(owners).hasSize(1);
    }

    @Test
    void findAllByLastNameLikeAllMatch() {
        String lastName = "kin";
        Collection<Owner> owners = ownerMapService.findAllByLastNameLike(lastName);
        assertThat(owners).hasSameSizeAs(ownerMapService.map.values());
    }

    @Test
    void findAllByLastNameLikeNoMatch() {
        String lastName = "foo_bar";
        Collection<Owner> owners = ownerMapService.findAllByLastNameLike(lastName);
        assertThat(owners).isEmpty();
    }
}
