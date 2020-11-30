package ons.group8.services;

import ons.group8.domain.ChecklistItem;
import ons.group8.domain.Users;
import org.apache.catalina.User;

import java.util.List;

public interface UserService {

    List<Users> findByFname(String name);

    Users findByEmail(String email);


}
