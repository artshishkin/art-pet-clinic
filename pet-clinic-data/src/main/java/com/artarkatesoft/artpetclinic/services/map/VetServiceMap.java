package com.artarkatesoft.artpetclinic.services.map;

import com.artarkatesoft.artpetclinic.model.Vet;
import com.artarkatesoft.artpetclinic.services.VetService;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class VetServiceMap extends AbstractMapService<Vet, Long> implements VetService {
    @Override
    public Vet save(Vet vet) {
        Long id = vet.getId() != null ?
                vet.getId() :
                map.keySet().stream().max(Comparator.naturalOrder()).orElse(1L);
        return this.save(id, vet);
    }
}
