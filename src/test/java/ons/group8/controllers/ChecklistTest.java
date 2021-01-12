package ons.group8.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import ons.group8.controllers.forms.ChecklistTemplateForm;
import ons.group8.controllers.forms.TopicForm;
import ons.group8.domain.ChecklistTemplateItem;
import ons.group8.domain.Topic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ChecklistTest {

    @Autowired
    private MockMvc mockMvc;


    public static String asJsonString(final Object obj) {
        // used this website for this: https://howtodoinjava.com/spring-boot2/testing/spring-boot-mockmvc-example/#:~:text=MockMVC%20class%20is%20part%20of,Spring%20boot%202%20hateoas%20example.
        // and also to learn how to use the testing for post requests in the controller
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public TopicForm generateTopic(Integer itemsNum) {
        TopicForm topicForm = new TopicForm();
        topicForm.setTopicTitle("topic 1");
        topicForm.setTopicDescription("desc 1");
        topicForm.setAnotherTopic("false");
        topicForm.setItems(new ArrayList<>());
        for (int i = 1; i <= itemsNum; i++) {
            topicForm.getItems().add("topic " + i);
        }

        return topicForm;
    }

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

    @Test
    @WithMockUser(roles = {"AUTHOR"})
    public void should_post_checklist_title_and_description() throws Exception {
        ChecklistTemplateForm checklistTemplateForm = new ChecklistTemplateForm();
        checklistTemplateForm.setTitle("Checklist Title");
        checklistTemplateForm.setTitleDescription("description");
        this.mockMvc
                .perform(post("/author/checklist-title-and-description")
                        .content(asJsonString(checklistTemplateForm))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"AUTHOR"})
    public void should_post_set_topic_with_5_items() throws Exception {
        ChecklistTemplateForm checklistTemplateForm = new ChecklistTemplateForm();
        checklistTemplateForm.setTitle("Checklist Title");
        checklistTemplateForm.setTitleDescription("description");
        TopicForm topicForm = generateTopic(5);
        System.out.println(checklistTemplateForm);
        System.out.println(topicForm);
        this.mockMvc
                .perform(post("/author/set-topic")
                        .content(asJsonString(topicForm))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
