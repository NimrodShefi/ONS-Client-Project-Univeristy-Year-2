package ons.group8.repositories;

import ons.group8.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface UserRepositoryJPA extends JpaRepository<User,Long> {

    Optional<User> findUsersByEmail(String email);

}
