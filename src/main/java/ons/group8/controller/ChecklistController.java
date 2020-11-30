package ons.group8.controller;


import ons.group8.domain.ChecklistItem;
import ons.group8.domain.PersonalChecklist;
import ons.group8.domain.Users;
import ons.group8.services.PersonalChecklistService;
import ons.group8.services.UserService;
import ons.group8.services.UserServiceImpl;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class ChecklistController {

    @Autowired
    private UserService userService;

    @Autowired
    private PersonalChecklistService personalChecklistService;


    @GetMapping("view-checklist-starter")
    public String viewChecklist(@RequestParam("email") String email, Integer userId, Model model){

        Users user = userService.findByEmail(email);
        PersonalChecklist personalChecklist = personalChecklistService.findByUsers(userId);


//        ChecklistItem checklistItem = new ChecklistItem("this task", "do this task", false);

        model.addAttribute("user", user);
        model.addAttribute("personalChecklist", personalChecklist);
//        model.addAttribute("checklistItem", checklistItem);

        return "viewChecklistStarter";
    }
}
