package ons.group8.controllers;

import ons.group8.controllers.forms.AssignedToForm;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ChecklistTest {

    @Autowired
    private MockMvc mockMvc;

    public ChecklistTemplateForm generateChecklistForm(Integer topicsNum, Integer itemsNum) {
        ChecklistTemplateForm checklistTemplateForm = new ChecklistTemplateForm();
        checklistTemplateForm.setTitle("Checklist Title");
        checklistTemplateForm.setTitleDescription("description");
        List<Topic> topics = new ArrayList<>();
        List<ChecklistTemplateItem> items = new ArrayList<>();
        if (topicsNum != 0) {
            for (int i = 1; i <= topicsNum; i++) {
                for (int j = 1; j <= itemsNum; j++) {
                    items.add(new ChecklistTemplateItem("item " + j));
                }
                topics.add(new Topic("topic " + i, "desc " + i, items));
            }
        }
        checklistTemplateForm.setTopics(topics);
        return checklistTemplateForm;
    }

    public TopicForm generateTopicForm(Integer itemsNum) {
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
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("title", checklistTemplateForm.getTitle())
                        .param("titleDescription", checklistTemplateForm.getTitleDescription())
                        .sessionAttr("checklistForm", new ChecklistTemplateForm())
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"AUTHOR"})
    public void should_post_set_topic_with_5_items() throws Exception {
        TopicForm topicForm = generateTopicForm(5);
        System.out.println(topicForm);
        this.mockMvc
                .perform(post("/author/set-topic")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("title", "Checklist Title")
                        .param("titleDescription", "description")
                        .sessionAttr("checklistForm", new ChecklistTemplateForm())
                        .param("topicTitle", topicForm.getTopicTitle())
                        .param("topicDescription", topicForm.getTopicDescription())
                        .param("items", String.valueOf(topicForm.getItems()))
                        .param("anotherTopic", topicForm.getAnotherTopic())
                        .sessionAttr("topicForm", new TopicForm())
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"AUTHOR"})
    public void should_post_assign_to_with_5_users() throws Exception {
        ChecklistTemplateForm checklistTemplateForm = generateChecklistForm(2, 4);
        System.out.println(checklistTemplateForm);
        List<Long> user_ids = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            user_ids.add((long) i);
        }
        this.mockMvc
                .perform(post("/author/assign-to")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("title", checklistTemplateForm.getTitle())
                        .param("titleDescription", checklistTemplateForm.getTitleDescription())
                        .param("topics", String.valueOf(checklistTemplateForm.getTopics()))
                        .sessionAttr("checklistForm", new ChecklistTemplateForm())
                        .param("deadline", "12/01/2021")
                        .param("id", String.valueOf(user_ids))
                        .sessionAttr("formValues", new AssignedToForm())
                )
                .andDo(print())
                .andExpect(status().isOk());
    }
}
