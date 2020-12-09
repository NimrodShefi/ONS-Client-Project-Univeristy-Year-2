package ons.group8.controllers;

import ons.group8.controllers.forms.ChecklistForm;
import ons.group8.domain.*;
import ons.group8.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class PersonalChecklistController {

    Logger logger = LoggerFactory.getLogger(PersonalChecklistController.class);

    private final PersonalChecklistService personalChecklistService;

    @Autowired
    public PersonalChecklistController(PersonalChecklistService aPersonalChecklistService) {
        personalChecklistService = aPersonalChecklistService;
    }

    @GetMapping("personal-checklist-list")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String listPersonalChecklists(Principal principal, Model model){
        logger.debug("Getting personal checklist list for user: " + principal.getName());
        List<PersonalChecklist> personalChecklists = personalChecklistService.findAllPersonalChecklistsByUserEmail(principal.getName());
        model.addAttribute("personalChecklists", personalChecklists);
        return "personal-checklist-list";
    }

    @GetMapping("personal-checklist/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String viewChecklist(@PathVariable("id") Long pChecklistId, Principal principal, Model model) {
        logger.debug("Getting personal checklist with id: " + pChecklistId);
        Optional<PersonalChecklist> personalChecklist = personalChecklistService.getById(pChecklistId);
        if (personalChecklist.isPresent()){
            logger.debug("Found personal checklist with id: " + pChecklistId);
            if (personalChecklistService.isUserAssignedToPersonalChecklist(personalChecklist.get(), principal.getName())) {
                model.addAttribute("personalChecklist", personalChecklist.get());
                model.addAttribute("checklistForm", new ChecklistForm(personalChecklistService.getCheckedItemIds(personalChecklist.get())));
                return "viewChecklistStarter";
            } else {
                logger.error("User " + principal.getName() + " is not authorised to access personal checklist with id: " + pChecklistId);
                throw new AccessDeniedException("You are not authorised to access this checklist");
            }
        } else {
            logger.error("Could not find personal checklist with id: " + pChecklistId);
            model.addAttribute("title", "404 - Not found");
            model.addAttribute("message", "The requested checklist was not found.");
            return "message";
        }
    }

    @PostMapping("/personal-checklist/{id}/save")
    public String saveChecklist(@PathVariable("id") Long pChecklistId, @ModelAttribute("checklistForm") ChecklistForm checklistForm, Model model) {
        logger.debug("Saving personal checklist with id " + pChecklistId);
        Optional<PersonalChecklist> personalChecklist = personalChecklistService.getById(pChecklistId);
        if (personalChecklist.isPresent()){
            logger.debug("Found personal checklist with id: " + pChecklistId);
            personalChecklistService.updateCheckedItems(personalChecklist.get(), checklistForm.getCheckedItemIds());
            model.addAttribute("personalChecklist", personalChecklist.get());
            model.addAttribute("checklistForm", checklistForm);
            return "viewChecklistStarter";
        } else {
            logger.error("Could not find personal checklist with id: " + pChecklistId);
            model.addAttribute("title", "404 - Not found");
            model.addAttribute("message", "The requested checklist was not found.");
            return "message";
        }
    }
}
