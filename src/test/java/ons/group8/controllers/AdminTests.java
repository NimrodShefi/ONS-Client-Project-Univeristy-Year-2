package ons.group8.controllers;

import ons.group8.controllers.forms.UserRoleForm;
import ons.group8.services.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AdminTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserServiceImpl userService;

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void should_get_user_roles_page() throws Exception {

        this.mockMvc
                .perform(get("/admin/user-roles"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void should_get_single_user_role_form_page() throws Exception {

        this.mockMvc
                .perform(get("/admin/userrole-form/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void should_post_which_single_user_is_edited() throws Exception {

        this.mockMvc
                .perform(post("/admin/userrole-form")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("user", "1")
                        .param("assignedRolesIds", "2")
                        .sessionAttr("userRoleForm", new UserRoleForm())
                )
                .andDo(print())
                .andExpect(status().isOk());
        assertTrue(userService.findById((long)1).get().getRoles().contains(userService.findRoleById(2).get()));

    }
}
