package ons.group8.controllers;

import ons.group8.controllers.dto.UserForm;
import ons.group8.domain.Roles;
import ons.group8.domain.Users;
import ons.group8.repository.RoleRepositoryJPA;
import ons.group8.service.AdminService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {

    private AdminService theAdminService;
    private RoleRepositoryJPA theRoleRepositoryJPA;

    @Autowired
    public AdminController(AdminService aAdminService, RoleRepositoryJPA aRoleRepositoryJPA ) {
        theAdminService = aAdminService;
        theRoleRepositoryJPA = aRoleRepositoryJPA;
    }


//    @ModelAttribute("allUsers") //it maps the model attribute to the html template
//    public List<Users> allUsers() {
//        return theAdminService.findAllUsers();
//    }

    //@ModelAttribute("allRoles")
    //public List<Users> allRoles() {
        //return theAdminService.findAll();
    //}

    @GetMapping("get-user")
    public String serveUserForm(Model model) {
        UserForm userForm = new UserForm(null, "", "",theRoleRepositoryJPA.findAll() );
        model.addAttribute("user", userForm);
        //model.addAttribute("roles", theRoleRepositoryJPA.findAll());
        return "user-form";
    }


    @PostMapping("get-user")
    public String handleUserForm(@ModelAttribute("user") UserForm aForm, BindingResult bindings ,Model model) {
        System.out.println(aForm);

        Long id = aForm.getId();
        String fname = aForm.getFirstName();
        String lname = aForm.getLastName();
        List<Roles> roles = aForm.getRoles();

        // dAT
        Users user = new Users(id, fname, lname, roles);


//        if (bindings.hasErrors()) {
//            System.out.println("Errors:" + bindings.getFieldErrorCount());
//            for (ObjectError oe : bindings.getAllErrors()) {
//                System.out.println(oe);
//            }
//            return "user-form";
//        } else {
//            //should always be valid at this point
//            Long id = aForm.getId();
//            String aName = aForm.getName();
//            String aName = aForm.getName();
//            List<String> roles = aForm.getOccasions();
//
//            Optional<User> aUser = AdminService.makeGreeting(id, aName, roles);
//
//            logIfNewOrRepeating(names, aName);
//
//            System.out.println("List of members: " + names);
//            if (aGreeting.isPresent()) { //belt and braces - shouldn't need to check here.
//                Greeting theGreeting = aGreeting.get();
//                //attributes.addFlashAttribute("lastGreeting", theGreeting);
//
//                //model.addAttribute("lastGreeting", theGreeting); - this won't work.  the data won't survive the re-direct.
//                //return "redirect:greetings/last";
//                return "redirect:greetings/" + theGreeting.getId(); //don't need the flash attribute now.
//
//            }
//        }
        theAdminService.updateUser(8L, user);
        model.addAttribute("users", theAdminService.findAll());
        return "user-list";
    }




//    @GetMapping("/Admin")
//    public String getAllUsers(Model model) {
//        model.addAttribute("Users", adminService.findAll());
////        model.addAttribute("roles",new AssignRolesRequestDTO());
//        return "Admin";
//    }
//
//
//
//    @PostMapping("/assign-roles")
//    public void AssignRoles( ArrayList<Users> usersList, AssignRolesRequestDTO assignRolesRequestDTO, Model model) {
//        System.out.println("printing");
//        System.out.println(assignRolesRequestDTO);
//        System.out.println(usersList);
////        adminService.assignRoles(assignRolesRequestDTO);
//    }

}