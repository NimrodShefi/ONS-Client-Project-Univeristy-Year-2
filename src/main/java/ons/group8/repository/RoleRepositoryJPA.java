package ons.group8.repository;

import ons.group8.domain.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepositoryJPA extends JpaRepository<Roles, Long>, UserRoleRepo{
}

