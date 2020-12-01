package ons.group8.repositories;

import ons.group8.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RoleRepositoryJPA extends JpaRepository<Role, Long> {

    List<Role> findAll();

    Optional<Role> findById(int id);
}

