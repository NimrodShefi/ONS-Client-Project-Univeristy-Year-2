package ons.group8.repositories;

import ons.group8.domain.Role;
import ons.group8.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepositoryJPA extends JpaRepository<User, Long> {

    Optional<User> findUsersByEmail(String email);

    boolean existsByEmail(String email);

    @Query("UPDATE User u SET u.failedAttempt = ?1 WHERE u.email = ?2")
    @Modifying
    public void updateFailedAttempts(int failAttempts, String email);

    User findUserByEmail(String email);

    List<User> findUsersByRoles(Role role);

    Set<User> findUsersByFirstName(String firstName);




}
