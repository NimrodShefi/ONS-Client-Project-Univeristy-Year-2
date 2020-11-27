package ons.group8.controller;


import ons.group8.domain.ChecklistItem;
import ons.group8.domain.Users;
import ons.group8.services.UserService;
import ons.group8.services.UserServiceImpl;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class ChecklistController {

    @Autowired
    private UserService userService;



    private User user;

    @GetMapping("view-checklist-starter")
    public String viewChecklist(Model model){

        List<Users> users = userService.findByFname("John");
        Users user = userService.findByEmail("john@hotmail.com");

        ChecklistItem checklistItem = new ChecklistItem("this task", "do this task", false);

        System.out.println(checklistItem.toString());
        System.out.println(users.toString());

        model.addAttribute("user", user);
        model.addAttribute("checklistItem", checklistItem);

        return "viewChecklistStarter";
    }
}
