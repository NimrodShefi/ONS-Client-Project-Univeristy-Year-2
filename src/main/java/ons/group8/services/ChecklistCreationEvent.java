package ons.group8.services;

import lombok.Value;
import ons.group8.domain.Topic;
import ons.group8.domain.User;

import java.util.List;

@Value
public class ChecklistCreationEvent {
    String title;
    String titleDescription;
    List<Topic> topics;
    List<User> assignedTo;
    String deadline;
    User authorId;
}
