package com.artarkatesoft.artpetclinic.controllers;

import com.artarkatesoft.artpetclinic.model.Owner;
import com.artarkatesoft.artpetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("owners")
public class OwnerController {

    static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";
    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    /**
     * Custom handler for displaying an owner.
     *
     * @param ownerId the ID of the owner to display
     * @return a ModelMap with the model attributes for the view
     */
    @GetMapping("{ownerId}")
    public ModelAndView showOwner(@PathVariable("ownerId") Long ownerId) {
        Owner owner = ownerService.findById(ownerId);
        ModelAndView mav = new ModelAndView("owners/ownerDetails");
        mav.addObject("owner", owner);
        return mav;
    }

    @GetMapping("find")
    public String initFindForm(Map<String, Object> model) {
        model.put("owner", new Owner());
        return "owners/findOwners";
    }

    @GetMapping
    public String processFindForm(Owner owner, BindingResult result, Map<String, Object> model) {

        // allow parameterless GET request for /owners to return all records
        if (owner.getLastName() == null) {
            owner.setLastName(""); // empty string signifies broadest possible search
        }

        // find owners by last name
        List<Owner> results = ownerService.findAllByLastNameLike(owner.getLastName());
        if (results.isEmpty()) {
            // no owners found
            result.rejectValue("lastName", "notFound", "not found");
            return "owners/findOwners";
        } else if (results.size() == 1) {
            // 1 owner found
            owner = results.iterator().next();
            return "redirect:/owners/" + owner.getId();
        } else {
            // multiple owners found
            model.put("selections", results);
            return "owners/ownersList";
        }
    }

    @GetMapping("new")
    public String initCreationForm(Model model) {
        model.addAttribute("owner", Owner.builder().build());
        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("new")
    public String processCreationForm(@Valid Owner owner, BindingResult result) {
        if (result.hasErrors()) {
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        } else {
            Owner savedOwner = ownerService.save(owner);
            return "redirect:/owners/" + savedOwner.getId();
        }
    }

    @GetMapping("{ownerId}/edit")
    public String initUpdateForm(@PathVariable("ownerId") Long ownerId, Model model) {
        Owner owner = ownerService.findById(ownerId);
        model.addAttribute("owner", owner);
        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("{ownerId}/edit")
    public String processUpdateForm(@PathVariable("ownerId") Long ownerId, Owner owner, BindingResult result) {
        if (result.hasErrors()) {
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        } else {
            Owner ownerInRepo = ownerService.findById(ownerId);
            if (ownerInRepo == null) throw new RuntimeException("Owner with id: " + ownerId + " not found");

            owner.setId(ownerId);
            ownerService.save(owner);
            return "redirect:/owners/{ownerId}";
        }
    }


}
