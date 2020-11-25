package ons.group8.controllers.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignRolesRequestDTO {

    private String role;
    private Long userId;
}
