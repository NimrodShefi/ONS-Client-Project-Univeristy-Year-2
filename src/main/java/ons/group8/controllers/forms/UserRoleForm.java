package ons.group8.controllers.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ons.group8.domain.Role;
import ons.group8.domain.User;

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

    private User user;

    private List<Role> roles;

    public UserRoleForm(User user, List<Role> roles) {
        this(null, null, user, roles);
    }
}