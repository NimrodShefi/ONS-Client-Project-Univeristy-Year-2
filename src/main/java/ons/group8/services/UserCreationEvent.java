package ons.group8.services;

import lombok.Value;

import javax.validation.constraints.Email;

@Value
public class UserCreationEvent {
    String email;
    String firstName;
    String lastName;
    String password;
    String repeatPassword;
}
