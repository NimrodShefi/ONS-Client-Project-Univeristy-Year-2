package ons.group8.services;

import ons.group8.domain.User;
import ons.group8.repositories.UserRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;


@Service
public class UserServiceImpl implements UserService{

    private UserRepositoryJPA userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder; //got password encoder from: https://www.baeldung.com/spring-security-registration-password-encoding-bcrypt

    @Autowired
    public UserServiceImpl(UserRepositoryJPA userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(UserCreationEvent newUser) throws SQLIntegrityConstraintViolationException{

        if (userRepository.existsByEmail(newUser.getEmail())){
            throw new SQLIntegrityConstraintViolationException(
                    "There is an account with that email address: " + newUser.getEmail()
            );
        }
        User user = new User(newUser.getEmail().toLowerCase(), passwordEncoder.encode(newUser.getPassword()), newUser.getFirstName(), newUser.getLastName());
        userRepository.save(user);
    }
}
