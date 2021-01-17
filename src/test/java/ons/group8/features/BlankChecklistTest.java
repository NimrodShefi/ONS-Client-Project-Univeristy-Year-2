package ons.group8.features;

import ons.group8.controllers.forms.AssignedToForm;
import ons.group8.controllers.forms.ChecklistTemplateForm;
import ons.group8.controllers.forms.TopicForm;
import ons.group8.domain.*;
import ons.group8.services.AuthorServiceImpl;
import ons.group8.services.ChecklistCreationEvent;
import ons.group8.services.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class BlankChecklistTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private AuthorServiceImpl authorService;

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

    public ChecklistCreationEvent generateChecklist(String title, String description, String deadline, int topicsNum, int itemsNum, int assignedToNum) {
        List<Topic> topics = new ArrayList<>();
        List<User> assignedTo = new ArrayList<>();
        for (int i = 1; i <= topicsNum; i++) {
            List<ChecklistTemplateItem> items = new ArrayList<>();
            for (int j = 1; j <= itemsNum; j++) {
                items.add(new ChecklistTemplateItem("Item " + j));
            }
            topics.add(new Topic("Topic " + i, "topic description " + i, items));
        }
        for (int i = 1; i <= assignedToNum ; i++) {
            assignedTo.add(userService.findById((long) i).get());
        }

        return new ChecklistCreationEvent(title, description,
                topics, assignedTo, deadline, userService.findById((long) 5).get());
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

    @Test
    public void should_create_checklist_template_with_2_topics_and_5_items_and_assigned_to_5_users() throws Exception {
        ChecklistTemplate checklistTemplate = authorService.save(generateChecklist("Checklist Title Test","Description Test", "15/01/2021", 2, 5, 5));
        ChecklistTemplate checklistTemplate1 = authorService.getChecklistTemplateById(checklistTemplate.getId());
        List<PersonalChecklist> personalChecklist = authorService.getAllByChecklistTemplate(checklistTemplate1);

        assertEquals(checklistTemplate1.getName(), "Checklist Title Test");
        assertEquals(checklistTemplate1.getTopics().size(), 2);
        assertEquals(checklistTemplate1.getTopics().get(0).getItems().size(), 5);
        assertEquals(personalChecklist.size(), 5);
    }

    @Test
    public void should_create_checklist_template_with_1_topic_and_10_items_and_assigned_to_2_users() throws Exception {
        ChecklistTemplate checklistTemplate = authorService.save(generateChecklist("Checklist Title Test","Description Test", "15/01/2021", 1, 10, 2));
        ChecklistTemplate checklistTemplate1 = authorService.getChecklistTemplateById(checklistTemplate.getId());
        List<PersonalChecklist> personalChecklist = authorService.getAllByChecklistTemplate(checklistTemplate1);

        assertEquals(checklistTemplate1.getName(), "Checklist Title Test");
        assertEquals(checklistTemplate1.getTopics().size(), 1);
        assertEquals(checklistTemplate1.getTopics().get(0).getItems().size(), 10);
        assertEquals(personalChecklist.size(), 2);
    }
}
