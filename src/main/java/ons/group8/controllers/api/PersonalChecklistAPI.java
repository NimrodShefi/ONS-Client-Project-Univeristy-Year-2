package ons.group8.controllers.api;

import ons.group8.domain.PersonalChecklist;
import ons.group8.services.PersonalChecklistService;
import ons.group8.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
public class PersonalChecklistAPI {

    private final PersonalChecklistService personalChecklistService;
    private final UserService userService;

    @Autowired
    public PersonalChecklistAPI(PersonalChecklistService aPersonalChecklistService, UserService aUserService) {
        personalChecklistService = aPersonalChecklistService;
        userService = aUserService;
    }

    @GetMapping("/personal-checklist/{id}/progress")
    public ChecklistProgressDTO getPersonalChecklistProgress(@PathVariable Long id, Model model) {
        Optional<PersonalChecklist> personalChecklist = personalChecklistService.getById(id);
        Long checklistItems = 0L;
        Long checkedItems = 0L;
        if (personalChecklist.isPresent()) {
            checklistItems = personalChecklistService.getChecklistItemsCount(personalChecklist.get());
            checkedItems = personalChecklistService.getCheckedItemsCount(personalChecklist.get());
        }
        return new ChecklistProgressDTO(checkedItems, checklistItems);
    }

}
