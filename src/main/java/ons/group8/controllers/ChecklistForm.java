package ons.group8.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ons.group8.domain.User;
import org.json.simple.JSONObject;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChecklistForm {
    private JSONObject checklistData;
    private List<User> allUsers;
}
