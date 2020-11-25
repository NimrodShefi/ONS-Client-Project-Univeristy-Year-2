package ons.group8.services;

import ons.group8.domain.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public interface UserService {
    public void save(UserCreationEvent user) throws Exception;

}
