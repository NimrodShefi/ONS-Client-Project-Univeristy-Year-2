package ons.group8.services;

import lombok.Value;

@Value
public class UserCreationEvent {
    String email;
    String firstName;
    String lastName;
    String password;
    String repeatPassword;
}
