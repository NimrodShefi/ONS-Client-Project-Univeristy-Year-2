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

    /**
     * with the data sent from the controller a new user will be created, but before that happens the data has to be validated to ensure that there aren't any problems
     *
     * @param newUser - this is the data collected from the controller sent to be saved as a user
     * @throws SQLIntegrityConstraintViolationException - when validating the data, should the email exist, this is the error that will be thrown
     * @throws DataFormatException                      - when validating the data, should any of the data have the wrong format, this error will be thrown
     */
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

    /**
     * checking if the name is empty and then if not to see if it passes the regex and contains only letters
     *
     * @param name - the piece of data validated
     * @param text - this method checks both first name and last name, so this will say in the message which part of the name this is for, when checking if the name is empty
     * @return - true/false to see if the name passes the regex
     * @throws DataFormatException - if the name is empty
     */
    private boolean nameValidation(String name, String text) throws DataFormatException {
        if (name.equals("") || name == null) {
            throw new DataFormatException(text + " is empty. Please input your name");
        }
        Matcher matcher = Pattern.compile("^[a-zA-Z]*$").matcher(name);

        return matcher.find();
    }

    /**
     * checking of the email is empty and then if matches the format of the emails wanted
     *
     * @param email - the piece of data validated
     * @return - true/false to see if the email passes the regex
     * @throws DataFormatException - if the email is empty
     */
    private boolean emailValidation(String email) throws DataFormatException {
        if (email.equals("") || email == null) {
            throw new DataFormatException("Email is empty. Please input your email");
        }
        Matcher matcher = Pattern.compile("^[^\\s@<>+*/=!\"£$%^&()`¬\\\\|;:?,#~]+@cardiff.ac.uk").matcher(email);

        return matcher.find();
    }

    /**
     * checking of the password is empty and then if matches the format of the passwords wanted
     *
     * @param password - the piece of data validated
     * @return - true/false to see if the password passes all the regex
     * @throws DataFormatException - if the password is empty
     */
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

    /**
     * checking of the repeat password is empty and then if the repeats password has the same value as password
     *
     * @param password       - using to validate the repeat password variable
     * @param repeatPassword - the piece of data validated
     * @return - true/false to see if the repeat password equals the password
     * @throws DataFormatException - if the repeat password is empty
     */
    private boolean samePasswordValidation(String password, String repeatPassword) throws DataFormatException {
        if (repeatPassword.equals("") || repeatPassword == null) {
            throw new DataFormatException("Please input your password in both fields");
        }
        return password.equals(repeatPassword);
    }

    /**
     * calling all of the methods shown above and checking their outputs, while throwing an error with teh correct message according to what went wrong
     *
     * @param user - the object holding all of the data that is going to be validated by the methods above
     * @throws DataFormatException                      -  if there is a problem with the format of any of the user details
     * @throws SQLIntegrityConstraintViolationException -  if the email signed in with already exists
     */
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
            } else if (!lastName) {
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
    public Optional<Role> findRoleById(long id) {
        return roleRepository.findById(id);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }
}
