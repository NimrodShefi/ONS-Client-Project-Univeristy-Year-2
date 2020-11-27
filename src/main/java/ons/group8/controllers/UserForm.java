package ons.group8.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserForm {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String repeatPassword;
}
