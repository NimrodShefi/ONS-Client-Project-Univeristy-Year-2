package ons.group8.controllers;


import ons.group8.domain.*;
import ons.group8.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class ChecklistController {

    @Autowired
    private UserService userService;

    @Autowired
    private PersonalChecklistService personalChecklistService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private ChecklistTemplateItemService checklistTemplateItemService;


    @GetMapping("view-checklist-starter")
    public String viewChecklist(@RequestParam("email") String email, Model model){

        User user = userService.findByEmail(email);
        PersonalChecklist personalChecklist = personalChecklistService.findByUserId_Id(user.getId());
        Topic topic = topicService.findByChecklistTemplate_Id(personalChecklist.getChecklistTemplate().getId());
        ChecklistTemplateItem checklistTemplateItem = checklistTemplateItemService.findByTopicId_Id(topic.getId());

        model.addAttribute("user", user);
        model.addAttribute("personalChecklist", personalChecklist);
        model.addAttribute("topic", topic);
        model.addAttribute("checklistTemplateItem", checklistTemplateItem);


        return "viewChecklistStarter";
    }
}
