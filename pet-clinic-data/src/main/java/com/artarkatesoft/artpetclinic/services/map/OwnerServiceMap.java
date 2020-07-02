package com.artarkatesoft.artpetclinic.services.map;

import com.artarkatesoft.artpetclinic.model.Owner;
import com.artarkatesoft.artpetclinic.services.OwnerService;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class OwnerServiceMap extends AbstractMapService<Owner, Long> implements OwnerService {
    @Override
    public Owner findByLastName(String lastName) {
        return this.findAll().stream().filter(owner -> owner.getLastName().equals(lastName)).findFirst().orElse(null);
    }

    @Override
    public Owner save(Owner owner) {
        Long id = owner.getId() != null ?
                owner.getId() :
                map.keySet().stream().max(Comparator.naturalOrder()).orElse(1L);
        return this.save(id, owner);
    }
}
