package ons.group8.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ons.group8.domain.Roles;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserForm {
    private Long roleId;
    private String email;
    private List<Roles> roles;
}
