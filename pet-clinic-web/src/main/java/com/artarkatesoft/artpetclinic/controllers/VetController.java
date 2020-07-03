package com.artarkatesoft.artpetclinic.controllers;

import com.artarkatesoft.artpetclinic.model.BaseEntity;
import com.artarkatesoft.artpetclinic.model.Vet;
import com.artarkatesoft.artpetclinic.services.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class VetController {

    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    @RequestMapping("vets")
    public String list(Model model) {

        List<Vet> vetList = vetService.findAll()
                .stream()
                .sorted(Comparator.comparing(BaseEntity::getId))
                .collect(Collectors.toList());
        model.addAttribute("vets", vetList);
        return "vets/index";
    }

}
