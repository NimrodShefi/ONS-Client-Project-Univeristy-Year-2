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

    @Autowired
    private ChecklistTemplateService checklistTemplateService;



    @GetMapping("view-checklist-starter")
    public String viewChecklist(@RequestParam("email") String email, Model model){

        User user = userService.findByEmail(email);
        PersonalChecklist personalChecklist = personalChecklistService.findByUserId_Id(user.getId());
        Topic topic = topicService.findByChecklistTemplate_Id(personalChecklist.getChecklistTemplate().getId());
        List<ChecklistTemplateItem> checklistTemplateItem = checklistTemplateItemService.findAllByTopicId_Id(topic.getId());
        ChecklistTemplate checklistTemplate = checklistTemplateService.findByUserId(user.getId());


        model.addAttribute("user", user);
        model.addAttribute("personalChecklist", personalChecklist);
        model.addAttribute("topic", topic);
        model.addAttribute("checklistTemplateItems", checklistTemplateItem);
        model.addAttribute("checklistTemplate", checklistTemplate);


        return "viewChecklistStarter";
    }
}
