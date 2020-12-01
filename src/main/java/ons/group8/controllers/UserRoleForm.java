package ons.group8.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ons.group8.domain.Role;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleForm {
    private Long roleId;
    private String email;
    private List<Role> roles;
}