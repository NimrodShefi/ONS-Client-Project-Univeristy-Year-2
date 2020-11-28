package ons.group8.service;

import ons.group8.domain.Roles;
import ons.group8.domain.Users;
import ons.group8.repository.UserRepositoryJPA;
import ons.group8.repository.UserRoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private UserRepositoryJPA userRepositoryJPA;


    @Autowired
    public AdminServiceImpl(UserRepositoryJPA aUserRepositoryJPA) {
        userRepositoryJPA = aUserRepositoryJPA;
    }

    @Override
    public List<Users> findAll() {
        return userRepositoryJPA.findAll();
    }

    @Override
    public void updateUser(Long id, Users user){
        userRepositoryJPA.save(userRepositoryJPA.findById(id).get());
    }





//    @Override
//    public void assignRoles(AssignRolesRequestDTO assignRolesRequestDTO) {
////        Optional<User> userExist=userRepository.findById(assignRolesRequestDTO.getUserId());
////        if(userExist.isEmpty()){
////            System.out.print("User dont exist");
////        }
////        else{
////            Optional<Role> roleExist= RoleRepository.findByName(assignRolesRequestDTO.getRole());
////            if(roleExist.isEmpty()){
////                System.out.print("ROle dont exist");
////            }
////            //userExist.get().setRoles(roleExist);
////           // userRepository.save(userExist.get());
////        }
//
//   }
}
