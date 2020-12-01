package ons.group8.controllers;

import lombok.extern.slf4j.Slf4j;
import ons.group8.domain.Role;
import ons.group8.domain.User;
import ons.group8.repositories.RoleRepositoryJPA;
import ons.group8.repositories.UserRepositoryJPA;
import ons.group8.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;
import java.util.*;

@Controller
@Slf4j
public class AdminController {

    private AdminService theAdminService;
    private RoleRepositoryJPA theRoleRepositoryJPA;
    private UserRepositoryJPA theUserRepositoryJPA;


    @Autowired
    public AdminController(AdminService aAdminService, RoleRepositoryJPA aRoleRepositoryJPA, UserRepositoryJPA aUserRepositoryJPA) {
        theAdminService = aAdminService;
        theRoleRepositoryJPA = aRoleRepositoryJPA;
        theUserRepositoryJPA = aUserRepositoryJPA;
    }


    @GetMapping("userrole-form")
    public String serveUserForm(Model model) {
        UserRoleForm userRoleForm = new UserRoleForm();
        userRoleForm.setRoles(theRoleRepositoryJPA.findAll());
        model.addAttribute("user", userRoleForm);
        return "userrole-form";
    }


    @PostMapping("userrole-form")
    public String handleUserForm(@Valid @ModelAttribute("user") UserRoleForm userRoleForm, BindingResult bindings, Model model) {
        Optional<User> userExist = theAdminService.findUsersByEmail(userRoleForm.getEmail());
        if(userExist.isEmpty())
            log.error("user not exist");
        Optional<Role> roleExist = theAdminService.findRolesById(userRoleForm.getRoleId());
        if(roleExist.isEmpty())
            log.error("roles does not exist");
        Set<Role> setRoles =new HashSet<>();
        setRoles.add(roleExist.get());
        userExist.get().setRoles(setRoles);
        theUserRepositoryJPA.save(userExist.get());
        model.addAttribute("users", theAdminService.findAll());
        return "user-roles";
        }
    }
