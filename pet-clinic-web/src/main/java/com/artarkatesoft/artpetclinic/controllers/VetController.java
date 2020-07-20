package com.artarkatesoft.artpetclinic.controllers;

import com.artarkatesoft.artpetclinic.model.BaseEntity;
import com.artarkatesoft.artpetclinic.model.Vet;
import com.artarkatesoft.artpetclinic.services.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class VetController {

    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    @RequestMapping({"vets", "vets.html"})
    public String list(Model model) {

        List<Vet> vetList = vetService.findAll()
                .stream()
                .sorted(Comparator.comparing(BaseEntity::getId))
                .collect(Collectors.toList());
        model.addAttribute("vets", vetList);
        return "vets/index";
    }

    @GetMapping(path = "api/vets")
    @ResponseBody
    public Set<Vet> getVetsJson() {
        return vetService.findAll();
    }

}
