package com.artarkatesoft.artpetclinic.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "types")
@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
@ToString(callSuper = true)
public class PetType extends BaseEntity {
    private String name;
}
