package ons.group8.service;


import ons.group8.domain.Users;
import java.util.List;

public interface AdminService {

    List<Users> findAll();

    void updateUser(Long userId, Long roleId);

    Long findUsersIdByEmail(String email);

}
