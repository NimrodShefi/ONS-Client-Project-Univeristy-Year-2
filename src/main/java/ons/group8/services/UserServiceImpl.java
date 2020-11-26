package ons.group8.services;

import ons.group8.domain.User;
import ons.group8.repositories.UserRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;


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
    public void save(UserCreationEvent newUser) throws SQLIntegrityConstraintViolationException, DataFormatException {

        if (userRepository.existsByEmail(newUser.getEmail())){
            throw new SQLIntegrityConstraintViolationException();
        }
        try {
            validateData(newUser);
            User user = new User(newUser.getEmail().toLowerCase(), passwordEncoder.encode(newUser.getPassword()), newUser.getFirstName(), newUser.getLastName());
            userRepository.save(user);
        } catch (DataFormatException e){
            throw e;
        }
    }

    public boolean emailValidation(String email){
        String regex = "^(([^<>()[\\]\\\\.,;:\\s@\\\"]+(\\.[^<>()[\\]\\\\.,;:\\s@\\\"]+)*)|(\\\".+\\\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    public boolean passwordValidation(String password){
        return password.matches("/[a-z]/g")
                && password.matches("/[A-Z]/g")
                && password.matches("/[0-9]/g")
                && password.matches("/[^a-zA-Z\\d]/g")
                && password.length() >= 8;
    }

    public boolean samePasswordValidation(String password, String repeatPassword){
        return password.equals(repeatPassword);
    }

    public boolean validateData(UserCreationEvent user) throws DataFormatException {
        boolean emailFormat = emailValidation(user.getEmail());
        boolean passwordFormat = passwordValidation(user.getPassword());
        boolean samePassword = samePasswordValidation(user.getPassword(), user.getRepeatPassword());

        if (emailFormat && passwordFormat && samePassword){
            return true;
        } else{
            throw new DataFormatException(
                    "There is a problem with the format of the data"
                            + " Email Format: " + emailFormat
                            + " Password Format: " + passwordFormat
                            + " Same Password: " + samePassword
            );
        }
    }
}
