package ons.group8.repository;

import ons.group8.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepositoryJPA extends JpaRepository<Users,Long> {


}
