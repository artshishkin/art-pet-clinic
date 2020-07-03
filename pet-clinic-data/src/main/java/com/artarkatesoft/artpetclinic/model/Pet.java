package com.artarkatesoft.artpetclinic.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Pet extends BaseEntity{
    private PetType petType;
    private Owner owner;
    private LocalDate birthDate;
}
