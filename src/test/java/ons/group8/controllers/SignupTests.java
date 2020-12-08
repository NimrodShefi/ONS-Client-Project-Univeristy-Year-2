package ons.group8.controllers;

import ons.group8.controllers.forms.UserForm;
import ons.group8.services.UserCreationEvent;
import ons.group8.services.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@SpringBootTest
@RunWith(SpringRunner.class)
@WebMvcTest(SignupController.class)
public class SignupTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void shouldGetSignUpPage() throws Exception {

        this.mockMvc
                .perform(get("/sign-up/add-user"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
