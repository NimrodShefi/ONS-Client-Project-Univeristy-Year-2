package ons.group8.controllers;

import ons.group8.controllers.forms.AssignedToForm;
import ons.group8.controllers.forms.ChecklistForm;
import ons.group8.controllers.forms.TopicForm;
import ons.group8.domain.User;
import ons.group8.domain.checklist.Topic;
import ons.group8.services.AuthorService;
import ons.group8.services.ChecklistCreationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/author")
@SessionAttributes("checklistForm")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    //getting the user id of the logged in person
    public Long getLoggedInUserId(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails){
            username = ((UserDetails)principal).getUsername();
        }else {
            username = principal.toString();
        }
        return authorService.findUserByEmail(username).getId();
    }

    @ModelAttribute("checklistForm")
    public ChecklistForm getChecklistForm() {
        return new ChecklistForm();
    }

    @GetMapping("view-my-checklists")
    public String viewMyChecklists(){
        return "checklist/view-all-checklists";
    }

    @GetMapping("checklist-title-and-description")
    public String startChecklistForm(Model model, @ModelAttribute("checklistForm") ChecklistForm checklistForm) {
        model.addAttribute("checklist", checklistForm);
        return "checklist/checklist-title-and-description";
    }

    @PostMapping("checklist-title-and-description")
    public String setTitleAndDescription(@ModelAttribute("checklistForm") ChecklistForm checklistForm, @Valid ChecklistForm formValues, BindingResult bindings, Model model) {
        if (bindings.hasErrors()) {
            System.out.println("Errors:" + bindings.getFieldErrorCount());
            for (ObjectError oe : bindings.getAllErrors()) {
                System.out.println(oe);
            }
            return "checklist/checklist-title-and-description";
        } else {
            checklistForm.setTitle(formValues.getTitle());
            checklistForm.setTitleDescription(formValues.getTitleDescription());
            model.addAttribute("title", formValues.getTitle());
            model.addAttribute("titleDescription", formValues.getTitleDescription());
            model.addAttribute("topicForm", new TopicForm());
            return "checklist/checklist-topic";
        }
    }

    @PostMapping("set-topic")
    public String setTopic(@ModelAttribute("checklistForm") ChecklistForm checklistForm, @Valid TopicForm topic, BindingResult bindings, Model model) {
        if (bindings.hasErrors()) {
            System.out.println("Errors:" + bindings.getFieldErrorCount());
            for (ObjectError oe : bindings.getAllErrors()) {
                System.out.println(oe);
            }
            return "checklist/checklist-topic";
        } else {
            checklistForm.getTopics().add(new Topic(topic.getTopicTitle(), topic.getTopicDescription(), topic.getItems()));
            model.addAttribute("title", checklistForm.getTitle());
            model.addAttribute("titleDescription", checklistForm.getTitleDescription());
            if ("true".equals(topic.getAnotherTopic())) {
                model.addAttribute("topicForm", new TopicForm());
                return "checklist/checklist-topic";
            } else {
                model.addAttribute("users", authorService.findAll());
                model.addAttribute("assignedTo", new AssignedToForm());
                return "checklist/assign-to";
            }
        }
    }

    @PostMapping("assign-to")
    public String setUsersToChecklist(@ModelAttribute("checklistForm") ChecklistForm checklistForm, @Valid AssignedToForm formValues, BindingResult bindings, Model model) {
        if (bindings.hasErrors()) {
            System.out.println("Errors:" + bindings.getFieldErrorCount());
            for (ObjectError oe : bindings.getAllErrors()) {
                System.out.println(oe);
            }
            return "checklist/assign-to";
        } else {
            checklistForm.setAssignedTo(formValues.getId());
            checklistForm.setDeadline(formValues.getDeadline());
            System.out.println(checklistForm);
            authorService.save(new ChecklistCreationEvent(checklistForm.getTitle(), checklistForm.getTitleDescription(),
                    checklistForm.getTopics(), checklistForm.getAssignedTo(), checklistForm.getDeadline(), getLoggedInUserId()));

            return "redirect:/";
        }
    }

}
