package com.artarkatesoft.artpetclinic.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "types")
@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
@SuperBuilder
public class PetType extends BaseEntity {
    private String name;
}
