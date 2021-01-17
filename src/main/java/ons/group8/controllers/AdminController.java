package ons.group8.controllers;

import lombok.extern.slf4j.Slf4j;
import ons.group8.controllers.forms.UserRoleForm;
import ons.group8.domain.Role;
import ons.group8.domain.User;
import ons.group8.services.AdminService;
import ons.group8.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final UserService userService;


    @Autowired
    public AdminController(AdminService aAdminService,
                           UserService aUserService) {
        adminService = aAdminService;
        userService = aUserService;
    }


    @GetMapping("user-roles")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getList(@RequestParam(value = "firstName", required = false) String searchName, Model model) {
        if (searchName == null) {
            List<User> users = adminService.findAllUsers();
            users.remove(userService.getLoggedInUserId());
            model.addAttribute("users", users);
            return "user-roles";
        } else {
            Set<User> users = userService.findUsersByFirstName(searchName);
            users.remove(userService.getLoggedInUserId());
            model.addAttribute("users", users);
            model.addAttribute("noUsersFound", users.isEmpty());
            return "user-roles";
        }
    }


    @GetMapping("userrole-form/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String serveUserForm(@PathVariable("userId") Long userId, Model model) {
        Optional<User> userExist = userService.findById(userId);
        if (userExist.isPresent() && userExist.get().getId().equals(userService.getLoggedInUserId().getId())){
            model.addAttribute("message", "You can't edit your own user");
            return "user-roles";
        }
        if (userExist.isPresent()) {
            Set<Long> userRoles = userExist.get().getRoles().stream()
                    .map(Role::getId)
                    .collect(Collectors.toSet());
            UserRoleForm userRoleForm = new UserRoleForm(userExist.get(), userRoles);
            model.addAttribute("userRoleForm", userRoleForm);
            model.addAttribute("allRoles", adminService.findAllRoles());
            return "userrole-form";
        } else {
            log.error("Could not user with id: " + userId + " while trying to serve user role form");
            model.addAttribute("title", "404 - Not found");
            model.addAttribute("message", "The requested user was not found.");
            return "message";
        }
    }

    @PostMapping("userrole-form")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String handleUserForm(@Valid @ModelAttribute("userRoleForm") UserRoleForm userRoleForm, BindingResult bindings, Model model) {
        if (bindings.hasErrors()) {
            model.addAttribute("allRoles", adminService.findAllRoles());
            System.out.println("errors = " + bindings.getAllErrors());
            return "userrole-form";
        }
        User userExist = userRoleForm.getUser();
        if (userExist == null) {
            log.error("user not exist");
            model.addAttribute("allRoles", adminService.findAllRoles());
            System.out.println("are there errors = " + bindings.hasErrors());
            System.out.println("errors = " + bindings.getAllErrors());
            return "userrole-form";
        }
        Set<Role> newRoles = userRoleForm
                .getAssignedRolesIds()
                .stream()
                .map(r -> adminService.findRolesById(r).get())
                .collect(Collectors.toSet());
        userExist.setRoles(newRoles);
        userService.update(userExist);
        model.addAttribute("users", adminService.findAllUsers());
        return "user-roles";
    }
}
