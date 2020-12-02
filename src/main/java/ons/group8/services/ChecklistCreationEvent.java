package ons.group8.services;

import lombok.Value;
import ons.group8.domain.checklist.Topic;

import java.util.List;

@Value
public class ChecklistCreationEvent {
    String title;
    String titleDescription;
    List<Topic> topics;
    List<Long> assignedTo;
    String deadline;
}
