package ons.group8.services;

import ons.group8.domain.User;
import ons.group8.domain.UserRole;
import ons.group8.repositories.UserRepositoryJPA;
import ons.group8.repositories.UserRoleRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepositoryJPA userRepository;
    private UserRoleRepositoryJPA userRoleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder; //got password encoder from: https://www.baeldung.com/spring-security-registration-password-encoding-bcrypt

    @Autowired
    public UserServiceImpl(UserRepositoryJPA userRepository, UserRoleRepositoryJPA userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void save(UserCreationEvent newUser) throws SQLIntegrityConstraintViolationException, DataFormatException {

        if (userRepository.existsByEmail(newUser.getEmail())) {
            throw new SQLIntegrityConstraintViolationException();
        }
        try {
            validateData(newUser);
            User user = new User(newUser.getEmail().toLowerCase(), passwordEncoder.encode(newUser.getPassword()), newUser.getFirstName(), newUser.getLastName());
            Integer userID = userRepository.save(user).getId(); // The save() method returns the saved entity, including the id field which was null up until now.
            UserRole userRole = new UserRole(userID, 3); // roleId = 3 is the basic user role, meaning that the default for nay new user would be lowest possible permission and then change it by the admin to whatever it is supposed to be
            userRoleRepository.save(userRole);
        } catch (Exception e) {
            throw e;
        }
    }

    private boolean passwordValidation(String password) {
        Matcher matcher1 = Pattern.compile(".*[a-z].*").matcher(password); // must contain lower-case
        Matcher matcher2 = Pattern.compile(".*[A-Z.].*").matcher(password); // must contain upper-case
        Matcher matcher3 = Pattern.compile(".*[\\d].*").matcher(password); // must contain a number
        Matcher matcher4 = Pattern.compile("[1-9a-zA-Z\\\\d]{8,}").matcher(password); // must have at least 8 alphanumeric numeric characters
        return matcher1.find()
                && matcher2.find()
                && matcher3.find()
                && matcher4.find();
    }

    private boolean samePasswordValidation(String password, String repeatPassword) {
        return password.equals(repeatPassword);
    }

    private boolean validateData(UserCreationEvent user) throws DataFormatException {
        boolean passwordFormat = passwordValidation(user.getPassword());
        boolean samePassword = samePasswordValidation(user.getPassword(), user.getRepeatPassword());

        if (passwordFormat && samePassword) {
            return true;
        } else {
            throw new DataFormatException();
        }
    }
}
