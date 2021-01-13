package ons.group8.controllers;

import ons.group8.controllers.forms.AssignedToForm;
import ons.group8.controllers.forms.ChecklistTemplateForm;
import ons.group8.controllers.forms.TopicForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class ChecklistTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * this will generate the data needed for the topics to be created in set-topic page
     *
     * @param itemsNum     - how many items the topic will have
     * @param anotherTopic - should another topic be created
     * @return - the created TopicForm
     */
    public TopicForm generateTopicForm(Integer itemsNum, String anotherTopic) {
        TopicForm topicForm = new TopicForm();
        topicForm.setTopicTitle("topic 1");
        topicForm.setTopicDescription("desc 1");
        topicForm.setAnotherTopic(anotherTopic);
        topicForm.setItems(new ArrayList<>());
        for (int i = 1; i <= itemsNum; i++) {
            topicForm.getItems().add("item " + i);
        }

        return topicForm;
    }

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

    @Test
    @WithMockUser(roles = {"AUTHOR"})
    public void should_post_checklist_title_and_description() throws Exception {
        ChecklistTemplateForm checklistTemplateForm = new ChecklistTemplateForm();
        checklistTemplateForm.setTitle("Checklist Title");
        checklistTemplateForm.setTitleDescription("description");
        this.mockMvc
                .perform(post("/author/checklist-title-and-description")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("title", checklistTemplateForm.getTitle())
                        .param("titleDescription", checklistTemplateForm.getTitleDescription())
                        .sessionAttr("checklistForm", new ChecklistTemplateForm())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("checklist/checklist-topic"));
    }

    @Test
    @WithMockUser(roles = {"AUTHOR"})
    public void should_post_set_topic_with_5_items_and_create_another_topic() throws Exception {
        TopicForm topicForm = generateTopicForm(5, "true");
        this.mockMvc
                .perform(post("/author/set-topic")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("topicTitle", topicForm.getTopicTitle())
                        .param("topicDescription", topicForm.getTopicDescription())
                        .param("items", topicForm.getItems().get(0))
                        .param("items", topicForm.getItems().get(1))
                        .param("items", topicForm.getItems().get(2))
                        .param("items", topicForm.getItems().get(3))
                        .param("items", topicForm.getItems().get(4))
                        .param("anotherTopic", topicForm.getAnotherTopic())
                        .sessionAttr("topicForm", new TopicForm())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("checklist/checklist-topic"));
    }

    @Test
    @WithMockUser(roles = {"AUTHOR"})
    public void should_post_set_topic_with_5_items_and_dont_create_another_topic() throws Exception {
        TopicForm topicForm = generateTopicForm(6, "false"); // it doesn't have to be false. It just can't be 'true'
        this.mockMvc
                .perform(post("/author/set-topic")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("topicTitle", topicForm.getTopicTitle())
                        .param("topicDescription", topicForm.getTopicDescription())
                        .param("items", topicForm.getItems().get(0))
                        .param("items", topicForm.getItems().get(1))
                        .param("items", topicForm.getItems().get(2))
                        .param("items", topicForm.getItems().get(3))
                        .param("items", topicForm.getItems().get(4))
                        .param("items", topicForm.getItems().get(5))
                        .param("anotherTopic", topicForm.getAnotherTopic())
                        .sessionAttr("topicForm", new TopicForm())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("checklist/assign-to"));
    }

    @Test
    @WithMockUser(roles = {"AUTHOR"})
    public void should_post_assign_to_with_5_users() throws Exception {
        List<Long> user_ids = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            user_ids.add((long) i);
        }
        this.mockMvc
                .perform(post("/author/assign-to")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("id", String.valueOf(user_ids.get(0)))
                        .param("id", String.valueOf(user_ids.get(1)))
                        .param("id", String.valueOf(user_ids.get(2)))
                        .param("id", String.valueOf(user_ids.get(3)))
                        .param("id", String.valueOf(user_ids.get(4)))
                        .param("deadline", "12/01/2021")
                        .sessionAttr("formValues", new AssignedToForm())
                )
                .andDo(print())
                .andExpect(status().isOk());
    }
}
