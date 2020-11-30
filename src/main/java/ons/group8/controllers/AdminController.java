package ons.group8.controllers;

import lombok.extern.slf4j.Slf4j;
import ons.group8.controllers.dto.UserForm;
import ons.group8.domain.Roles;
import ons.group8.domain.Users;
import ons.group8.repository.RoleRepositoryJPA;
import ons.group8.repository.UserRepositoryJPA;
import ons.group8.service.AdminService;
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


    @GetMapping("get-user")
    public String serveUserForm(Model model) {
        UserForm userForm = new UserForm();
        userForm.setRoles(theRoleRepositoryJPA.findAll());
        model.addAttribute("user", userForm);
        return "user-form";
    }


    @PostMapping("get-user")
    public String handleUserForm(@Valid @ModelAttribute("user") UserForm userForm, BindingResult bindings, Model model) {
        Optional<Users> userExist = theAdminService.findUsersByEmail(userForm.getEmail());
        if(userExist.isEmpty())
            log.error("user not exist");
        Optional<Roles> roleExist = theAdminService.findRolesById(userForm.getRoleId());
        if(roleExist.isEmpty())
            log.error("roles does not exist");
        Set<Roles> setRoles =new HashSet<>();
        setRoles.add(roleExist.get());
        userExist.get().setRoles(setRoles);
        theUserRepositoryJPA.save(userExist.get());
        model.addAttribute("users", theAdminService.findAll());
        return "user-list";
        }
    }
