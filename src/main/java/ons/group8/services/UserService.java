package ons.group8.services;

import ons.group8.domain.Role;
import ons.group8.domain.User;
import java.util.Optional;
import java.util.Set;

public interface UserService {
    void save(UserCreationEvent user) throws Exception;

    void update(User user);

    User findByEmail(String email);

    Optional<User> findById(Long userId);

    User getLoggedInUserId();

    Set<User> findUsersByFirstName(String firstName);

    Optional<Role> findRoleById(long id);
}