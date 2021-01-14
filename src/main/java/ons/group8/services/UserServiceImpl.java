package ons.group8.services;

import ons.group8.domain.Role;
import ons.group8.domain.User;
import ons.group8.repositories.RoleRepositoryJPA;
import ons.group8.repositories.UserRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;
import java.util.Set;
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
        try {
            validateData(newUser);
            Role userRole = roleRepository.getRoleByName("USER");
            User user = new User(newUser.getEmail().toLowerCase(), passwordEncoder.encode(newUser.getPassword()), newUser.getFirstName(), newUser.getLastName());
            user.addRole(userRole);
            userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private boolean nameValidation(String name, String text) throws DataFormatException {
        if (name.equals("") || name == null) {
            throw new DataFormatException(text + " is empty. Please input your name");
        }
        Matcher matcher = Pattern.compile("^[a-zA-Z]*$").matcher(name);

        return matcher.find();
    }

    private boolean emailValidation(String email) throws DataFormatException {
        if (email.equals("") || email == null) {
            throw new DataFormatException("Email is empty. Please input your email");
        }
        Matcher matcher = Pattern.compile("^[^\\s@<>+*/=!\"£$%^&()`¬\\\\|;:?,#~]+@cardiff.ac.uk").matcher(email);

        return matcher.find();
    }

    private boolean passwordValidation(String password) throws DataFormatException {
        if (password.equals("") || password == null) {
            throw new DataFormatException("Password is empty. Please input your password");
        }
        Matcher matcher1 = Pattern.compile(".*[a-z].*").matcher(password); // must contain lower-case
        Matcher matcher2 = Pattern.compile(".*[A-Z.].*").matcher(password); // must contain upper-case
        Matcher matcher3 = Pattern.compile(".*[\\d].*").matcher(password); // must contain a number
        Matcher matcher4 = Pattern.compile("[1-9a-zA-Z\\\\d]{8,}").matcher(password); // must have at least 8 alphanumeric numeric characters
        return matcher1.find()
                && matcher2.find()
                && matcher3.find()
                && matcher4.find();
    }

    private boolean samePasswordValidation(String password, String repeatPassword) throws DataFormatException {
        if (repeatPassword.equals("") || repeatPassword == null) {
            throw new DataFormatException("Please input your password in both fields");
        }
        return password.equals(repeatPassword);
    }

    private void validateData(UserCreationEvent user) throws DataFormatException, SQLIntegrityConstraintViolationException {
        boolean emailFormat = emailValidation(user.getEmail());
        boolean passwordFormat = passwordValidation(user.getPassword());
        boolean samePassword = samePasswordValidation(user.getPassword(), user.getRepeatPassword());
        boolean firstName = nameValidation(user.getFirstName(), "First Name");
        boolean lastName = nameValidation(user.getLastName(), "Last Name");

        if (!(passwordFormat && samePassword && emailFormat && firstName && lastName)) {
            if (!passwordFormat) {
                throw new DataFormatException("Password Format is wrong");
            } else if (!samePassword) {
                throw new DataFormatException("Passwords don't match");
            } else if (!emailFormat) {
                throw new DataFormatException("Email Format is wrong");
            } else if (!firstName) {
                throw new DataFormatException("First Name can only contain letters");
            } else if (!lastName){
                throw new DataFormatException("Last Name can only contain letters");
            }
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new SQLIntegrityConstraintViolationException("Email already exists");
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
    public User getLoggedInUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return findByEmail(username);
    }

    @Override
    public Set<User> findUsersByFirstName(String firstName) {
        return userRepository.findUsersByFirstName(firstName);
    }

    @Override
    public Optional<Role> findRoleById(long id){
        return roleRepository.findById(id);
    }

    @Override
    public void update(User user){
        userRepository.save(user);
    }
}
