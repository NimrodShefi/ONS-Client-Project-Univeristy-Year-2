package ons.group8.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ons.group8.domain.Topic;
import ons.group8.domain.User;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChecklistForm {
    private String title;
    private String titleDescription;
    private List<String> topics;
    private Long[] assignedTo;
    private String deadline;
    private List<User> allUsers;
}
