package ons.group8.controller;


import ons.group8.domain.ChecklistItem;
import ons.group8.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChecklistController {

    @GetMapping("view-checklist-starter")
    public String viewChecklist(Model model){

        User user = new User("john@hotmail.com", "p", "John", "Block");
        ChecklistItem checklistItem = new ChecklistItem("this task", "do this task", false);

        System.out.println(checklistItem.toString());
        System.out.println(user.toString());

        model.addAttribute("user", user);
        model.addAttribute("checklistItem", checklistItem);

        return "viewChecklistStarter";
    }
}
