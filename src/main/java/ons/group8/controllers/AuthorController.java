package ons.group8.controllers;

import ons.group8.controllers.forms.AssignedToForm;
import ons.group8.controllers.forms.ChecklistForm;
import ons.group8.controllers.forms.TopicForm;
import ons.group8.domain.ChecklistTemplate;
import ons.group8.domain.ChecklistTemplateItem;
import ons.group8.domain.Topic;
import ons.group8.domain.User;
import ons.group8.services.AuthorService;
import ons.group8.services.ChecklistCreationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public User getLoggedInUserId(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails){
            username = ((UserDetails)principal).getUsername();
        }else {
            username = principal.toString();
        }
        return authorService.findUserByEmail(username);
    }

    @ModelAttribute("checklistForm")
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public ChecklistForm getChecklistForm() {
        return new ChecklistForm();
    }

    @GetMapping("view-my-checklists")
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public String viewMyChecklists(){
        return "checklist/view-all-checklists";
    }

    @GetMapping("view-my-checklists/{id}")
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public String viewMyChecklist(@PathVariable(name = "id", required = false) Long checklistId, Model model){
        if (checklistId == null){
            return "checklist/view-all-checklists";
        } else {
            ChecklistTemplate checklistTemplate = authorService.getChecklistTemplateById(checklistId);
            System.out.println(checklistTemplate);
            System.out.println(checklistTemplate.getTopics());
            model.addAttribute("checklist", checklistTemplate);
            model.addAttribute("topics", checklistTemplate.getTopics());
            return "checklist/view-checklist";
        }
    }

    @GetMapping("checklist-title-and-description")
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public String startChecklistForm(Model model, @ModelAttribute("checklistForm") ChecklistForm checklistForm) {
        model.addAttribute("checklist", checklistForm);
        return "checklist/checklist-title-and-description";
    }

    @PostMapping("checklist-title-and-description")
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
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
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public String setTopic(@ModelAttribute("checklistForm") ChecklistForm checklistForm, @Valid TopicForm topic, BindingResult bindings, Model model) {
        if (bindings.hasErrors()) {
            System.out.println("Errors:" + bindings.getFieldErrorCount());
            for (ObjectError oe : bindings.getAllErrors()) {
                System.out.println(oe);
            }
            return "checklist/checklist-topic";
        } else {
            List<ChecklistTemplateItem> items = new ArrayList<>();
            for (String item : topic.getItems()) {
                items.add(new ChecklistTemplateItem(item));
            }
            checklistForm.getTopics().add(new Topic(topic.getTopicTitle(), topic.getTopicDescription(), items));
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
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public String setUsersToChecklist(@ModelAttribute("checklistForm") ChecklistForm checklistForm, @Valid AssignedToForm formValues, BindingResult bindings, Model model) {
        if (bindings.hasErrors()) {
            System.out.println("Errors:" + bindings.getFieldErrorCount());
            for (ObjectError oe : bindings.getAllErrors()) {
                System.out.println(oe);
            }
            return "checklist/assign-to";
        } else {
            List<User> users = new ArrayList<>();
            for (Long userId : formValues.getId()) {
                users.add(authorService.findUserById(userId));
            }
            checklistForm.setAssignedTo(users);
            checklistForm.setDeadline(formValues.getDeadline());
            System.out.println(checklistForm);
            try {
                authorService.save(new ChecklistCreationEvent(checklistForm.getTitle(), checklistForm.getTitleDescription(),
                        checklistForm.getTopics(), checklistForm.getAssignedTo(), checklistForm.getDeadline(), getLoggedInUserId()));
                model.addAttribute("title", "Process Completed");
                model.addAttribute("message", "The checklist is created and saved");
                return "message";
            } catch (Exception e) {
                model.addAttribute("title", "Process Failed!");
                model.addAttribute("message", "The checklist failed to be created");
                return "message";
            }
        }
    }
}
