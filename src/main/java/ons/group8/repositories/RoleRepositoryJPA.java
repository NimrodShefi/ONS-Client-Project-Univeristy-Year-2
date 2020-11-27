package ons.group8.repositories;

import ons.group8.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepositoryJPA extends JpaRepository<Role, Long> {
    Role getRoleByName(String name);
}
