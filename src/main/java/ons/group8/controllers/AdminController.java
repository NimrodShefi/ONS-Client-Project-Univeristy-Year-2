package ons.group8.controllers;

import ons.group8.controllers.dto.AssignRolesRequestDTO;
import ons.group8.service.AdminService;
import ons.group8.service.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

//    public AdminController(@Autowired AdminService adminService) {
//        this.adminServiceImpl = adminService;
//    }

    @GetMapping(path="/Admin")
    public String getAllUsers(Model model) {
        model.addAttribute("Users", adminService.findAll());
        return "Admin";
    }


    @PostMapping("/admin/assign-roles")
    public String AssignRoles(AssignRolesRequestDTO assignRolesRequestDTO) {
        adminService.assignRoles(assignRolesRequestDTO);
        return "redirect:/customers";
    }
//
}