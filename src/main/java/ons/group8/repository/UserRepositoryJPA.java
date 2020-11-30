package ons.group8.repository;

import ons.group8.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface UserRepositoryJPA extends JpaRepository<Users,Long> {

    Optional<Users> findUsersByEmail(String email);

}
