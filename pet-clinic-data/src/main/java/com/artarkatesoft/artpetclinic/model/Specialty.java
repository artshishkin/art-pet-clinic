package com.artarkatesoft.artpetclinic.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "specialties")
@Getter
@Setter
public class Specialty extends BaseEntity {
    @Column(name = "description")
    private String description;
}
