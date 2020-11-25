package ons.group8.service;

import ons.group8.controllers.dto.AssignRolesRequestDTO;
import ons.group8.domain.Role;
import ons.group8.domain.Users;
import ons.group8.repository.RoleRepository;
import ons.group8.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {


    @Autowired
    UserRepository userRepository;

    public List<Users> findAll() {
        List<Users> users = userRepository.findAll();
        return users;
    }

    @Override
    public void assignRoles(AssignRolesRequestDTO assignRolesRequestDTO) {
//        Optional<User> userExist=userRepository.findById(assignRolesRequestDTO.getUserId());
//        if(userExist.isEmpty()){
//            System.out.print("User dont exist");
//        }
//        else{
//            Optional<Role> roleExist= RoleRepository.findByName(assignRolesRequestDTO.getRole());
//            if(roleExist.isEmpty()){
//                System.out.print("ROle dont exist");
//            }
//            //userExist.get().setRoles(roleExist);
//           // userRepository.save(userExist.get());
//        }

   }
}
