package ons.group8.services;

import ons.group8.domain.User;
import java.util.Optional;

public interface UserService {
    public void save(UserCreationEvent user) throws Exception;

    User findByEmail(String email);

    Optional<User> findById(Long userId);

    void increaseFailedAttempts(User user);

    void resetFailedAttempts(String email);

    void lock(User user);

    boolean unlockWhenTimeExpired(User user);

}