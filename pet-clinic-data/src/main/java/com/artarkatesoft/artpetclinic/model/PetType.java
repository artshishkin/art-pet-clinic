package com.artarkatesoft.artpetclinic.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "types")
@EqualsAndHashCode(callSuper = true)
@Data
public class PetType extends BaseEntity {
    private String name;
}
