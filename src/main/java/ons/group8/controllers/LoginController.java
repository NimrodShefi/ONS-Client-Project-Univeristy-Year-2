package ons.group8.controllers;

import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Getter
public class LoginController {

    @GetMapping("/")
    public String getPage(Model model) {
        return "index";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/login-failed")
    public String loginFailed(final Model model) {
        System.out.println("Getting login failed page");
        model.addAttribute("error", "login-failed");
        return "login";
    }
}
