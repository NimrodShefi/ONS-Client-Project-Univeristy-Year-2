package ons.group8.controllers;

import ons.group8.domain.*;
import ons.group8.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Principal;
import java.util.List;

@Controller
public class PersonalChecklistController {

    Logger logger = LoggerFactory.getLogger(PersonalChecklistController.class);

    private final UserService userService;
    private final PersonalChecklistService personalChecklistService;

    @Autowired
    public PersonalChecklistController(UserService aUserService, PersonalChecklistService aPersonalChecklistService) {
        userService = aUserService;
        personalChecklistService = aPersonalChecklistService;
    }

    @GetMapping("personal-checklist-list")
    public String listPersonalChecklists(Principal principal, Model model){
        logger.info(String.format("Getting personal checklist list for user: %s", principal.getName()));
        List<PersonalChecklist> personalChecklists = personalChecklistService.findAllPersonalChecklistsByUserEmail(principal.getName());
        model.addAttribute("personalChecklists", personalChecklists);
        return "personal-checklist-list";
    }

//    @GetMapping("view-checklist-starter")
//    public String viewChecklist(@RequestParam("email") String email, Model model){
//
//        User user = userService.findByEmail(email);
//        PersonalChecklist personalChecklist = personalChecklistService.findByUserId_Id(user.getId());
//
//        model.addAttribute("user", user);
//        model.addAttribute("personalChecklist", personalChecklist);
//
//        return "viewChecklistStarter";
//    }
}
