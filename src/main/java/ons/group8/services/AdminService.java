package ons.group8.services;

import ons.group8.domain.Role;
import ons.group8.domain.User;
import java.util.List;
import java.util.Optional;

public interface AdminService {

    List<User> findAll();

    Long findUsersIdByEmail(String email);

    Optional<User> findUsersByEmail(String email);

    Optional<Role> findRolesById(Long id);



}
