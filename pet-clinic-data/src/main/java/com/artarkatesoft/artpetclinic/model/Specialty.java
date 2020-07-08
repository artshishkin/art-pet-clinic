package com.artarkatesoft.artpetclinic.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "specialties")
@Setter
@Getter
@SuperBuilder
public class Specialty extends BaseEntity {
    @Column(name = "description")
    private String description;
}
