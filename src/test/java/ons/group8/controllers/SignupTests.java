package ons.group8.controllers;

import ons.group8.domain.User;
import ons.group8.repositories.UserRepositoryJPA;
import ons.group8.services.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class SignupTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @Autowired
    private UserRepositoryJPA userRepository;

    @Test
    public void shouldGetSignUpPage() throws Exception {

        this.mockMvc
                .perform(get("/sign-up/add-user"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldAddUser() throws Exception {
        User user = new User("shefin@cardiff.ac.uk", "Password1!", "Nimrod", "Shefi");

        userRepository.save(user);
    }
}
