package ons.group8.controllers.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ons.group8.domain.Role;
import ons.group8.domain.User;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleForm {
    private Long roleId;

    private User user;

    private List<Role> roles;

    public UserRoleForm(User user, List<Role> roles) {
        this(null, user, roles);
    }
}