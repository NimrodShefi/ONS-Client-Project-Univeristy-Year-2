package ons.group8.features;

import ons.group8.controllers.forms.ChecklistForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonalChecklistTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser() // default is USER, which is what needed for this controller
    public void should_get_view_all_checklists_page() throws Exception {

        this.mockMvc
                .perform(get("/user/personal-checklist-list"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    public void should_get_view_personal_checklist_with_id_1() throws Exception {

        this.mockMvc
                .perform(get("/user/personal-checklist/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    public void should_post_changes_to_personal_checklist_with_id_1() throws Exception {

        this.mockMvc
                .perform(post("/user/personal-checklist/1/save")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("checkedItemIds", "1")
                        .param("checkedItemIds", "2")
                        .param("checkedItemIds", "3")
                        .param("checkedItemIds", "4")
                        .param("checkedItemIds", "5")
                        .param("checkedItemIds", "6")
                        .param("checkedItemIds", "7")
                        .sessionAttr("checklistForm", new ChecklistForm())

                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("viewChecklistStarter"));
    }

    @Test
    @WithMockUser(roles = {"AUTHOR", "ADMIN"})
    public void should_fail_to_get_personal_checklist_list_page() throws Exception {

        this.mockMvc
                .perform(get("/user/personal-checklist-list"))
                .andDo(print())
                .andExpect(status().isForbidden()); // isForbidden gives https status of 403, which is what is thrown when trying to access a page without permission
    }

    @Test
    @WithMockUser()
    public void should_fail_to_get_view_personal_checklist_with_id_100_as_it_doesnt_exists() throws Exception {

        this.mockMvc
                .perform(get("/user/personal-checklist/100"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("message"));
    }

    @Test
    @WithMockUser()
    public void should_fail_to_get_view_personal_checklist_with_id_100_after_save_as_it_doesnt_exists() throws Exception {

        this.mockMvc
                .perform(get("/user/personal-checklist/100/save"))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed());
    }
}
