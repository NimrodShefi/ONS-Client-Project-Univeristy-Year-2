package ons.group8.controllers;

import ons.group8.domain.User;
import ons.group8.repositories.UserRepositoryJPA;
import ons.group8.services.UserCreationEvent;
import ons.group8.services.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.zip.DataFormatException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class SignupTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepositoryJPA userRepository;

    @Test
    public void should_get_signup_page() throws Exception {

        this.mockMvc
                .perform(get("/sign-up/register"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void should_add_user() throws Exception {
        UserCreationEvent user = new UserCreationEvent("nimrodshefi@cardiff.ac.uk", "Nimrod", "Shefi", "Password1!", "Password1!");
        userService.save(user);

        User retrievedUser = userRepository.findUserByEmail(user.getEmail());
        assertEquals("nimrodshefi@cardiff.ac.uk", retrievedUser.getEmail());
    }

    @Test
    public void should_fail_to_add_user_due_to_wrong_password_format() throws Exception {
        try {
            UserCreationEvent user = new UserCreationEvent("nimrodshefi@cardiff.ac.uk", "Nimrod", "Shefi", "123", "123");
            userService.save(user);

            User retrievedUser = userRepository.findUserByEmail(user.getEmail());
            assertEquals(user.getEmail(), retrievedUser.getEmail());
        } catch (DataFormatException dataFormatException) {
            assertEquals("Password Format is wrong", dataFormatException.getMessage());
        }
    }

    @Test
    public void should_fail_to_add_user_due_to_wrong_email_format_1() throws Exception {
        try {
            UserCreationEvent user = new UserCreationEvent("nimrodshefi@gamil.com", "Nimrod", "Shefi", "Password1!", "Password1!");
            userService.save(user);

            User retrievedUser = userRepository.findUserByEmail(user.getEmail());
            assertEquals(user.getEmail(), retrievedUser.getEmail());
        } catch (DataFormatException dataFormatException) {
            assertEquals("Email Format is wrong", dataFormatException.getMessage());
        }
    }

    @Test
    public void should_fail_to_add_user_due_to_wrong_email_format_2() throws Exception {
        try {
            UserCreationEvent user = new UserCreationEvent("nimrodshefi<@cardiff.ac.uk", "Nimrod", "Shefi", "Password1!", "Password1!");
            userService.save(user);

            User retrievedUser = userRepository.findUserByEmail(user.getEmail());
            assertEquals(user.getEmail(), retrievedUser.getEmail());
        } catch (DataFormatException dataFormatException) {
            assertEquals("Email Format is wrong", dataFormatException.getMessage());
        }
    }

    @Test
    public void should_fail_to_add_user_due_to_email_already_exists() throws Exception {
        try {
            UserCreationEvent user = new UserCreationEvent("nimrodshefi@cardiff.ac.uk", "Nimrod", "Shefi", "Password1!", "Password1!");
            UserCreationEvent user2 = new UserCreationEvent("nimrodshefi@cardiff.ac.uk", "Nimrod", "Shefi", "Password1!", "Password1!");
            userService.save(user);
            userService.save(user2);

            User retrievedUser = userRepository.findUserByEmail(user.getEmail());
            assertEquals(user.getEmail(), retrievedUser.getEmail());
        } catch (SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException) {
            assertEquals("Email already exists.", sqlIntegrityConstraintViolationException.getMessage());
        }
    }
}
