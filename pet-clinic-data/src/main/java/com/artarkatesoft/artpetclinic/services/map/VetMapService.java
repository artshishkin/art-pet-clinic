package com.artarkatesoft.artpetclinic.services.map;

import com.artarkatesoft.artpetclinic.model.Specialty;
import com.artarkatesoft.artpetclinic.model.Vet;
import com.artarkatesoft.artpetclinic.services.SpecialtyService;
import com.artarkatesoft.artpetclinic.services.VetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VetMapService extends AbstractMapService<Vet, Long> implements VetService {

    private final SpecialtyService specialtyService;

    @Override
    public Vet save(Vet vet) {
        vet.getSpecialties().forEach(specialty -> {
            if (specialty==null) throw new RuntimeException("Specialty must not be null");
            if (specialty.getId()==null){
                Specialty savedSpecialty = specialtyService.save(specialty);
                specialty.setId(savedSpecialty.getId());
            }
        });

        return super.save(vet);
    }
}
