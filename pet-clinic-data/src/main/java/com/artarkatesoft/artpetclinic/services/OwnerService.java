package com.artarkatesoft.artpetclinic.services;

import com.artarkatesoft.artpetclinic.model.Owner;

public interface OwnerService extends CrudService<Owner, Long> {
    Owner findByLastName(String lastName);
}
