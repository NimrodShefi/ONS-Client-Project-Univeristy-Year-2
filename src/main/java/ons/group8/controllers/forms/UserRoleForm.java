package ons.group8.controllers.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ons.group8.domain.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleForm {
    private Long roleId;

    @Email @NotNull @NotBlank
    private String email;

    private List<Role> roles;
}