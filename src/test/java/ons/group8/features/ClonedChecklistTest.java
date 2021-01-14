package ons.group8.features;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ClonedChecklistTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = {"AUTHOR"})
    public void should_get_view_of_all_existing_checklists_page() throws Exception {

        this.mockMvc
                .perform(get("/author/create-from-clone/checklist-templates-list"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"AUTHOR"})
    public void should_get_view_of_existing_checklist_of_id_1() throws Exception {

        this.mockMvc
                .perform(get("/author/create-from-clone/checklist-template/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"AUTHOR"})
    public void should_clone_checklist_of_id_1_with_message_of_success() throws Exception {

        this.mockMvc
                .perform(get("/author/create-from-clone/checklist-template/1/clone"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
