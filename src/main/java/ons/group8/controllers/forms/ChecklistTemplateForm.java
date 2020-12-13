package ons.group8.controllers.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ons.group8.domain.Topic;
import ons.group8.domain.User;
import org.springframework.context.annotation.Scope;


import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Scope("session") // this applies to a user's individual session
public class ChecklistTemplateForm {
    private String title;
    private String titleDescription;
    private List<Topic> topics;
    private List<User> assignedTo;
    private String deadline;

    public List<Topic> getTopics(){
        if (topics == null){
            topics = new ArrayList<>();
        }
        return topics;
    }

    public List<User> getAssignedTo(){
        if (topics == null){
            topics = new ArrayList<>();
        }
        return assignedTo;
    }
}
