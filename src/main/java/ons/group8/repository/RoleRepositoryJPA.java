package ons.group8.repository;

import ons.group8.domain.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RoleRepositoryJPA extends JpaRepository<Roles, Long> {

    List<Roles> findAll();

    Optional<Roles> findById(int id);
}

