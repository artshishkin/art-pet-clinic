package com.artarkatesoft.artpetclinic.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pets")
@Data
@EqualsAndHashCode(callSuper = true)
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
