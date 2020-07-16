package com.artarkatesoft.artpetclinic.formatters;

import com.artarkatesoft.artpetclinic.model.PetType;
import com.artarkatesoft.artpetclinic.services.PetTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class PetTypeFormatter implements Formatter<PetType> {

    private final PetTypeService service;

    @Override
    public PetType parse(String name, Locale locale) throws ParseException {
        try {
            return service.findByName(name);
        } catch (RuntimeException e) {
            throw new ParseException(e.getMessage(), 0);
        }
    }

    @Override
    public String print(PetType petType, Locale locale) {
        return petType.getName();
    }
}
