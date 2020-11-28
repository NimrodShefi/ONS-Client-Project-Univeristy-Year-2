package ons.group8.service;


import ons.group8.domain.Users;

import java.util.List;

public interface AdminService {

    List<Users> findAll();

    void updateUser(Long id, Users user);

}
