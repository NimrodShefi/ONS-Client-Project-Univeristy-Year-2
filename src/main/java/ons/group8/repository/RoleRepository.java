package ons.group8.repository;

import ons.group8.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    static Optional<Role> findByName(String role) {
        return null;
    }
}

