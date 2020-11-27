package ons.group8.repositories;

import ons.group8.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepositoryJPA extends JpaRepository<UserRole, Long> {
}
