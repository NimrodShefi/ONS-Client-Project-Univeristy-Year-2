package ons.group8.services;

import ons.group8.domain.Role;
import ons.group8.domain.User;
import ons.group8.repositories.RoleRepositoryJPA;
import ons.group8.repositories.UserRepositoryJPA;
import ons.group8.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepositoryJPA userRepository;
    private final RoleRepositoryJPA roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; //got password encoder from: https://www.baeldung.com/spring-security-registration-password-encoding-bcrypt

    @Autowired
    public UserServiceImpl(UserRepositoryJPA userRepository, RoleRepositoryJPA roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void save(UserCreationEvent newUser) throws SQLIntegrityConstraintViolationException, DataFormatException {

        if (userRepository.existsByEmail(newUser.getEmail())) {
            throw new SQLIntegrityConstraintViolationException();
        }
        try {
            validateData(newUser);
            Role userRole = roleRepository.getRoleByName("USER");
            User user = new User(newUser.getEmail().toLowerCase(), passwordEncoder.encode(newUser.getPassword()), newUser.getFirstName(), newUser.getLastName());
            user.addRole(userRole);
            userRepository.save(user); // The save() method returns the saved entity, including the id field which was null up until now.
        } catch (Exception e) {
            throw e;
        }
    }

    private boolean emailValidation(String email) {
        Matcher matcher = Pattern.compile("^[^\\s@<>+*/=!\"£$%^&()`¬\\\\|;:?,#~]+@cardiff.ac.uk").matcher(email);

        return matcher.find();
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

    private void validateData(UserCreationEvent user) throws DataFormatException {
        boolean emailFormat = emailValidation(user.getEmail());
        boolean passwordFormat = passwordValidation(user.getPassword());
        boolean samePassword = samePasswordValidation(user.getPassword(), user.getRepeatPassword());

        if (!(passwordFormat && samePassword && emailFormat)) {
            if (!passwordFormat){
                throw new DataFormatException("Password Format is wrong");
            } else if (!samePassword){
                throw new DataFormatException("Passwords don't match");
            } else if (!emailFormat){
                throw new DataFormatException("Email Format is wrong");
            }
        }
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    //getting the user id of the logged in person
    public User getLoggedInUserId(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails){
            username = ((UserDetails)principal).getUsername();
        }else {
            username = principal.toString();
        }
        return findByEmail(username);
    }
}
