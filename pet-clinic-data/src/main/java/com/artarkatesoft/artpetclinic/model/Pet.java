package com.artarkatesoft.artpetclinic.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pets")
@Setter
@Getter
@SuperBuilder
@ToString(callSuper = true)
public class Pet extends BaseEntity {
    private String name;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn
    private PetType petType;

    private LocalDate birthDate;

    @ManyToOne
    @JoinColumn
    private Owner owner;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Visit> visits = new HashSet<>();
}
