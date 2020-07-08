package com.artarkatesoft.artpetclinic.services.map;

import com.artarkatesoft.artpetclinic.model.Visit;
import com.artarkatesoft.artpetclinic.services.VisitService;
import org.springframework.stereotype.Service;

@Service
public class VisitMapService extends AbstractMapService<Visit, Long> implements VisitService {
    @Override
    public Visit save(Visit visit) {
        if (visit == null) return null;
        if (visit.getPet() == null || visit.getPet().getOwner() == null ||
                visit.getPet().getId() == null || visit.getPet().getOwner().getId() == null)
            throw new RuntimeException("Invalid Visit");

        return super.save(visit);
    }
}
