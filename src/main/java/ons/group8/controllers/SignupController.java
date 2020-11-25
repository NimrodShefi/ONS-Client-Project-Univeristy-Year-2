package ons.group8.controllers;

import ons.group8.domain.User;
import ons.group8.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Controller
public class SignupController {

    private UserService userService;

    @Autowired
    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("save-user")
    public String saveUser(@RequestParam("fname") String firstName, @RequestParam("lname") String lastName,
                           @RequestParam("email") String email, @RequestParam("password") String password, Model model) throws NoSuchAlgorithmException {
        String hashedPassword = toHexString(getSHA(password));
        User user = new User(email, hashedPassword,firstName, lastName);
        System.out.println(user.toString());
        userService.createUser(user);
        model.addAttribute("user", user);
        return "test";
    }

    // functions getSHA and toHexString were taken from: https://www.geeksforgeeks.org/sha-256-hash-in-java/?ref=rp
    public static byte[] getSHA(String input) throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }
    public static String toHexString(byte[] hash){
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while (hexString.length() < 32){
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }
}
