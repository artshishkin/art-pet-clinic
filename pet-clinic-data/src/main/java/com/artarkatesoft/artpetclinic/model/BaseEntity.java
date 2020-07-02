package com.artarkatesoft.artpetclinic.model;


import java.io.Serializable;

public class BaseEntity implements Serializable {

    private Long i;

    public Long getI() {
        return i;
    }

    public void setI(Long i) {
        this.i = i;
    }
}
