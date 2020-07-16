package com.artarkatesoft.artpetclinic.formatters;

import com.artarkatesoft.artpetclinic.model.PetType;
import com.artarkatesoft.artpetclinic.services.PetTypeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test Pet Type Formatter")
class PetTypeFormatterTest {
    @Mock
    PetTypeService service;

    @InjectMocks
    PetTypeFormatter formatter;

    @Test
    @DisplayName("when pet type exists then parse should be ok")
    void parse_petTypeExist() throws ParseException {
        //given
        PetType petType = PetType.builder().id(1L).name("Dog").build();
        given(service.findByName(anyString())).willReturn(petType);
        //when
        PetType petTypeParsed = formatter.parse("Dog", Locale.getDefault());
        //then
        then(service).should().findByName(eq("Dog"));
        assertThat(petTypeParsed).isEqualTo(petType);
    }

    @Test
    @DisplayName("when pet type DOES NOT exist then exception should be thrown")
    void parse_petTypeNotExist() {
        //given
        given(service.findByName(anyString())).willThrow(RuntimeException.class);
        //when
        Executable parsing = () -> formatter.parse("FooFoo", Locale.getDefault());
        //then
        assertThrows(ParseException.class, parsing);
        then(service).should().findByName(eq("FooFoo"));
    }

    @Test
    @DisplayName("should print name of pet type")
    void print() {
        //given
        PetType petType = PetType.builder().id(1L).name("Mouse").build();
        //when
        String printPetType = formatter.print(petType, Locale.getDefault());
        //then
        assertThat(printPetType).isEqualTo("Mouse");
        then(service).shouldHaveNoInteractions();
    }
}
