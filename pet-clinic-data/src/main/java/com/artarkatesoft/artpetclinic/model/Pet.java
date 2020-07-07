package com.artarkatesoft.artpetclinic.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name="pets")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Pet extends BaseEntity{
    private String name;

    @ManyToOne
    @JoinColumn
    private PetType petType;

    private LocalDate birthDate;

    @ManyToOne
    @JoinColumn
    private Owner owner;
}
