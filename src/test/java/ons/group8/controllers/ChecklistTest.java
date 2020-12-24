package ons.group8.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ChecklistTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * These tests checks if the user accessing the page has permission to access the page. Only authors should be allowed
     */
    @Test
    @WithMockUser(roles = {"AUTHOR"})
    public void should_get_view_all_checklists_page() throws Exception {

        this.mockMvc
                .perform(get("/author/view-checklist-templates"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    public void should_fail_to_get_view_all_checklists_page() throws Exception {

        this.mockMvc
                .perform(get("/author/view-checklist-templates"))
                .andDo(print())
                .andExpect(status().isForbidden()); // isForbidden gives https status of 403, which is what is thrown when trying to access a page without permission
    }

    @Test
    @WithMockUser(roles = {"AUTHOR"})
    public void should_get_checklist_title_and_description() throws Exception {

        this.mockMvc
                .perform(get("/author/checklist-title-and-description"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    public void should_fail_to_get_checklist_title_and_description() throws Exception {

        this.mockMvc
                .perform(get("/author/checklist-title-and-description"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
