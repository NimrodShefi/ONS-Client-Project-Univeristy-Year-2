package ons.group8.controllers.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicForm {
    private String topicTitle;
    private String topicDescription;
    private List<String> items;
    private String anotherTopic;
}
