package ons.group8.controllers;

import ons.group8.controllers.forms.UserForm;
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

@Controller
@RequestMapping("/sign-up")
public class SignupController {

    private final UserService userService;

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
                model.addAttribute("title", "User Created");
                model.addAttribute("message", newUser.getFirstName() + " " + newUser.getLastName() +" Your username is :" + newUser.getEmail());
                return "message";
            } catch (Exception e) {
                model.addAttribute("error", e.toString().split(" ",2)[1]);
                return "add-user";
            }
        }
    }
}
