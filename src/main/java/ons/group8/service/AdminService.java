package ons.group8.service;

import ons.group8.controllers.dto.AssignRolesRequestDTO;
import ons.group8.domain.Users;
import java.util.List;

public interface AdminService {

    public void assignRoles(AssignRolesRequestDTO assignRolesRequestDTO);

    List<Users> findAll();


}
