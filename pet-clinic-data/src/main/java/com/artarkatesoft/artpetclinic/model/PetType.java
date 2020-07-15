package com.artarkatesoft.artpetclinic.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "types")
@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
public class PetType extends BaseEntity {
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
