package com.artarkatesoft.artpetclinic.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pets")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class Pet extends BaseEntity {
    private String name;

    @ManyToOne
    @JoinColumn
    private PetType petType;

    private LocalDate birthDate;

    @ManyToOne
    @JoinColumn
    @ToString.Exclude
    private Owner owner;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Visit> visits = new HashSet<>();
}
