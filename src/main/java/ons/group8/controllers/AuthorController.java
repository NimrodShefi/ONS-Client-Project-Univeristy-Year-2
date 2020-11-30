package ons.group8.controllers;

import ons.group8.domain.Topic;
import ons.group8.domain.User;
import ons.group8.services.AuthorService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/author")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("add-checklist")
    public String addChecklistForm(Model model) {
        ChecklistForm checklistForm = new ChecklistForm();
        checklistForm.setAllUsers(authorService.findAll());
        model.addAttribute("checklistForm", checklistForm);
        return "checklist-format";
    }

    @PostMapping("add-checklist")
    public String sendToChecklistItems(@Valid ChecklistForm checklistForm, BindingResult bindings, Model model) {
        if (bindings.hasErrors()) {
            System.out.println("Errors:" + bindings.getFieldErrorCount());
            for (ObjectError oe : bindings.getAllErrors()) {
                System.out.println(oe);
            }
            return "checklist-format";
        } else {
            model.addAttribute("checklistForm", checklistForm);
            return "checklist-items";
        }
    }

    @PostMapping("finish-checklist")
    public String addChecklist(@Valid ChecklistForm checklistForm, BindingResult bindings, Model model) {
        if (bindings.hasErrors()) {
            System.out.println("Errors:" + bindings.getFieldErrorCount());
            for (ObjectError oe : bindings.getAllErrors()) {
                System.out.println(oe);
            }
            return "checklist-format";
        } else {
            List<Topic> topicsList = new ArrayList<>();
            for (int i = 0; i < checklistForm.getTopics().size(); i+=2) {
                topicsList.add(new Topic(checklistForm.getTopics().get(i), checklistForm.getTopics().get(i+1)));
            }
            model.addAttribute("checklistForm", checklistForm);
            model.addAttribute("topics", topicsList);
            return "checklist-items";
        }
    }

    @PostMapping("create-checklist")
    public String createChecklist(@Valid List<Topic> topics, @Valid ChecklistForm checklistForm, BindingResult bindings){
        if (bindings.hasErrors()) {
            System.out.println("Errors:" + bindings.getFieldErrorCount());
            for (ObjectError oe : bindings.getAllErrors()) {
                System.out.println(oe);
            }
            System.out.println(topics);
            System.out.println(checklistForm);
            System.out.println("hello");
            return "checklist-items";
        } else {
            System.out.println(topics);
            System.out.println(checklistForm);
            System.out.println("test");
        }

        return "redirect:/";
    }

}
