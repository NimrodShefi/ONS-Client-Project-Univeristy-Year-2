package ons.group8.repositories;

import ons.group8.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface RoleRepositoryJPA extends JpaRepository<Role, Long> {

    Role getRoleByName(String name);

    List<Role> findAll();

    Optional<Role> findById(int id);
}
