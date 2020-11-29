package ons.group8.controllers;

import ons.group8.controllers.dto.UserForm;
import ons.group8.repository.RoleRepositoryJPA;
import ons.group8.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import javax.validation.Valid;

@Controller
public class AdminController {

    private AdminService theAdminService;
    private RoleRepositoryJPA theRoleRepositoryJPA;

    @Autowired
    public AdminController(AdminService aAdminService, RoleRepositoryJPA aRoleRepositoryJPA) {
        theAdminService = aAdminService;
        theRoleRepositoryJPA = aRoleRepositoryJPA;
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
        System.out.println(userForm);
        Long roleId = userForm.getRoleId();
        String email = userForm.getEmail();
        Long userId = theAdminService.findUsersIdByEmail(email);
//        List<Roles> roles = userForm.getRoles();
//        Users user = new Users(id, fname, lname, roles);
        theAdminService.updateUser(userId, roleId);
        model.addAttribute("users", theAdminService.findAll());
        return "user-list";
    }
}