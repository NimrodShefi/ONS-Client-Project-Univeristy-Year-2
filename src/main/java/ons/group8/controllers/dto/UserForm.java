package ons.group8.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ons.group8.domain.Roles;

import java.util.List;


@Data
@AllArgsConstructor
public class UserForm {
    private Long id;
    private String firstName;
    private String lastName;
    private List<Roles> roles;
}
