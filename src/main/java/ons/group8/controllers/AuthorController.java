package ons.group8.controllers;

import ons.group8.domain.User;
import ons.group8.services.AuthorService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/author")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("add-checklist")
    public String addChecklistForm(Model model) {
        List<User> users = authorService.findAll();
        model.addAttribute("users", users);
        return "add-checklist";
    }

    @PostMapping("create-checklist")
    public String addChecklist(@Valid JSONObject checklistData, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "add-checklist";
        } else {
            System.out.println(checklistData);
        }

        return "redirect:/";
    }

}
