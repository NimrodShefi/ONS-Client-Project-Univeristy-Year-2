package ons.group8.services;

import ons.group8.domain.Users;

import java.util.List;

public interface UserService {

    List<Users> findByFname(String name);

    Users findByEmail(String email);
}
