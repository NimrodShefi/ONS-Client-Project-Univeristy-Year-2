package ons.group8.services;


import ons.group8.domain.ChecklistItem;
import ons.group8.domain.Users;
import ons.group8.repositories.UserRepositoryJPA;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepositoryJPA userRepository;

    @Override
    public List<Users> findByFname(String name) {
        return userRepository.findByFname(name);
    }

    @Override
    public Users findByEmail(String email){
        return userRepository.findByEmail(email);
    }


}
