package com.artarkatesoft.artpetclinic.model;


import lombok.Data;

import java.io.Serializable;

@Data
public class BaseEntity implements Serializable {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
