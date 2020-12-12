package ons.group8.services;

import ons.group8.domain.Role;
import ons.group8.domain.User;
import ons.group8.repositories.RoleRepositoryJPA;
import ons.group8.repositories.UserRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    private UserRepositoryJPA userRepositoryJPA;
    private RoleRepositoryJPA roleRepositoryJPA;

    @Autowired
    public AdminServiceImpl(UserRepositoryJPA aUserRepositoryJPA, RoleRepositoryJPA aRoleRepositoryJPA) {
        userRepositoryJPA = aUserRepositoryJPA;
        roleRepositoryJPA = aRoleRepositoryJPA;
    }

    @Override
    public List<User> findAll() {
        System.out.print(userRepositoryJPA.findAll());
        return userRepositoryJPA.findAll();
    }

    @Override
    public Long findUsersIdByEmail(String email){
        return userRepositoryJPA.findUsersByEmail(email).get().getId();
    }

    @Override
    public Optional<User> findUsersByEmail(String email){
        return userRepositoryJPA.findUsersByEmail(email);
    }

    @Override
    public Optional<Role> findRolesById(Long id){
        return roleRepositoryJPA.findById(id);
    }


}
