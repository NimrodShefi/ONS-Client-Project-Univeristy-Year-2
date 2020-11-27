package ons.group8.repositories;

import ons.group8.domain.Users;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepositoryJPA extends JpaRepository<Users, Long> {

    List<Users> findByFname(String name);

    Users findByEmail(String email);

}
