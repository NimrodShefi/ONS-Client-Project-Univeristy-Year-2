package ons.group8.controllers;

import ons.group8.services.UserCreationEvent;
import ons.group8.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.zip.DataFormatException;

@Controller
@RequestMapping("/user")
public class SignupController {

    private UserService userService;

    @Autowired
    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("add-user")
    public String addUserForm(Model model) {
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        return "add-user";
    }

    @PostMapping("create-user")
    public String addUser(@Valid UserForm newUser, BindingResult bindingResult, Model model) throws Exception {
        if (bindingResult.hasErrors()) {
            return "add-user";
        } else {
            try {
                userService.save(new UserCreationEvent(newUser.getEmail(), newUser.getFirstName(), newUser.getLastName(), newUser.getPassword(), newUser.getRepeatPassword()));
            } catch (Exception e) {
                model.addAttribute("error", "There is a problem in the form" + e);
                return "add-user";
            }
        }

        return "redirect:/";
    }
}
