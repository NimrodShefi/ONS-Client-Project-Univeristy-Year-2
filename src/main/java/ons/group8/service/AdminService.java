package ons.group8.service;

import ons.group8.domain.Roles;
import ons.group8.domain.Users;
import java.util.List;
import java.util.Optional;

public interface AdminService {

    List<Users> findAll();

    Long findUsersIdByEmail(String email);

    Optional<Users> findUsersByEmail(String email);

    Optional<Roles> findRolesById(int id);

}
