package ons.group8.controllers;

import ons.group8.controllers.forms.AssignedToForm;
import ons.group8.controllers.forms.ChecklistForm;
import ons.group8.controllers.forms.TopicForm;
import ons.group8.domain.*;
import ons.group8.services.AuthorService;
import ons.group8.services.ChecklistCreationEvent;
import ons.group8.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/author")
@SessionAttributes("checklistForm")
public class AuthorController {

    Logger logger = LoggerFactory.getLogger(AuthorController.class);

    private final AuthorService authorService;

    private final UserService userService;

    @Autowired
    public AuthorController(AuthorService authorService, UserService userService) {
        this.authorService = authorService;
        this.userService = userService;
    }

    @ModelAttribute("checklistForm")
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public ChecklistForm getChecklistForm() {
        return new ChecklistForm();
    }

    @GetMapping("view-checklist-templates")
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public String viewChecklistTemplates(Principal principal, Model model){
        logger.info(String.format("Getting checklist template list for author: %s", principal.getName()));
        List<ChecklistTemplate> checklistTemplates = authorService.getAllByAuthorEmail(principal.getName());
        model.addAttribute("checklistTemplates", checklistTemplates);
        return "checklist/view-all-checklist-templates";
    }

    @GetMapping("view-checklist-template/{id}")
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public String viewMyChecklistTemplate(@PathVariable(name = "id", required = false) Long checklistId, Model model){
        if (checklistId == null){
            return "checklist/view-all-checklist-templates";
        } else {
            try {
                ChecklistTemplate checklistTemplate = authorService.getChecklistTemplateById(checklistId);
                List<PersonalChecklist> personalChecklists = authorService.getAllByChecklistTemplate(checklistTemplate);
                System.out.println(checklistTemplate.getTopics().get(0).getItems());
                model.addAttribute("checklist", checklistTemplate);
                model.addAttribute("users", personalChecklists);
                return "checklist/view-checklist-template";
            } catch (NullPointerException e){
                model.addAttribute("title", "Missing Checklist");
                model.addAttribute("message", "The checklist you are looking for could not be found");
                return "message";
            }
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
                users.add(userService.findById(userId).get());
            }
            checklistForm.setAssignedTo(users);
            checklistForm.setDeadline(formValues.getDeadline());
            System.out.println(checklistForm);
            try {
                authorService.save(new ChecklistCreationEvent(checklistForm.getTitle(), checklistForm.getTitleDescription(),
                        checklistForm.getTopics(), checklistForm.getAssignedTo(), checklistForm.getDeadline(), userService.getLoggedInUserId()));
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
