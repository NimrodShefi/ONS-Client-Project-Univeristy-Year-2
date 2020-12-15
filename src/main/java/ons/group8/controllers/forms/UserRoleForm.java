package ons.group8.controllers.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ons.group8.domain.User;

import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleForm {

    private User user;

    private Set<Long> assignedRolesIds;

}