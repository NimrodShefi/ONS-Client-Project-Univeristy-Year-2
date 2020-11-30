package ons.group8.service;

import ons.group8.domain.Roles;
import ons.group8.domain.Users;
import ons.group8.repository.RoleRepositoryJPA;
import ons.group8.repository.UserRepositoryJPA;
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
    public List<Users> findAll() {
        return userRepositoryJPA.findAll();
    }

    @Override
    public Long findUsersIdByEmail(String email){
        return userRepositoryJPA.findUsersByEmail(email).get().getId();
    }

    @Override
    public Optional<Users> findUsersByEmail(String email){
        return userRepositoryJPA.findUsersByEmail(email);
    }

    @Override
    public Optional<Roles> findRolesById(int id){
        return roleRepositoryJPA.findById(id);
    }

}
