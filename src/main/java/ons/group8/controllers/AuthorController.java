package ons.group8.controllers;

import ons.group8.controllers.forms.AssignedToForm;
import ons.group8.controllers.forms.ChecklistTemplateForm;
import ons.group8.controllers.forms.TopicForm;
import ons.group8.domain.*;
import ons.group8.repositories.RoleRepositoryJPA;
import ons.group8.services.AuthorService;
import ons.group8.services.ChecklistCreationEvent;
import ons.group8.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

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

    private final RoleRepositoryJPA roleRepository;

    @Autowired
    public AuthorController(AuthorService authorService, UserService userService, RoleRepositoryJPA roleRepository) {
        this.authorService = authorService;
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @ModelAttribute("checklistForm")
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public ChecklistTemplateForm getChecklistForm() {
        return new ChecklistTemplateForm();
    }

    @GetMapping("view-checklist-templates")
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public String viewChecklistTemplates(Principal principal, Model model){
        logger.debug("Getting checklist template list for author: " + principal.getName());
        System.out.println(getChecklistForm());
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
                model.addAttribute("checklist", checklistTemplate);
                model.addAttribute("personalChecklists", personalChecklists);
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
    public String startChecklistForm(Model model, @ModelAttribute("checklistForm") ChecklistTemplateForm checklistTemplateForm) {
        model.addAttribute("checklist", checklistTemplateForm);
        return "checklist/checklist-title-and-description";
    }

    @PostMapping("checklist-title-and-description")
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public String setTitleAndDescription(@ModelAttribute("checklistForm") ChecklistTemplateForm checklistTemplateForm, @Valid ChecklistTemplateForm formValues, BindingResult bindings, Model model) {
        if (bindings.hasErrors()) {
            System.out.println("Errors:" + bindings.getFieldErrorCount());
            for (ObjectError oe : bindings.getAllErrors()) {
                System.out.println(oe);
            }
            return "checklist/checklist-title-and-description";
        } else {
            checklistTemplateForm.setTitle(formValues.getTitle());
            checklistTemplateForm.setTitleDescription(formValues.getTitleDescription());
            model.addAttribute("title", formValues.getTitle());
            model.addAttribute("titleDescription", formValues.getTitleDescription());
            model.addAttribute("topicForm", new TopicForm());
            return "checklist/checklist-topic";
        }
    }

    @PostMapping("set-topic")
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public String setTopic(@ModelAttribute("checklistForm") ChecklistTemplateForm checklistTemplateForm, @Valid TopicForm topic, BindingResult bindings, Model model) {
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
            checklistTemplateForm.getTopics().add(new Topic(topic.getTopicTitle(), topic.getTopicDescription(), items));
            model.addAttribute("title", checklistTemplateForm.getTitle());
            model.addAttribute("titleDescription", checklistTemplateForm.getTitleDescription());
            if ("true".equals(topic.getAnotherTopic())) {
                model.addAttribute("topicForm", new TopicForm());
                return "checklist/checklist-topic";
            } else {
                model.addAttribute("users", authorService.findUsersByRoles(roleRepository.getRoleByName("USER")));
                model.addAttribute("assignedTo", new AssignedToForm());
                return "checklist/assign-to";
            }
        }
    }

    @PostMapping("assign-to")
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public String setUsersToChecklist(SessionStatus status, @ModelAttribute("checklistForm") ChecklistTemplateForm checklistTemplateForm, @Valid AssignedToForm formValues, BindingResult bindings, Model model) {
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
            checklistTemplateForm.setAssignedTo(users);
            checklistTemplateForm.setDeadline(formValues.getDeadline());
            try {
                authorService.save(new ChecklistCreationEvent(checklistTemplateForm.getTitle(), checklistTemplateForm.getTitleDescription(),
                        checklistTemplateForm.getTopics(), checklistTemplateForm.getAssignedTo(), checklistTemplateForm.getDeadline(), userService.getLoggedInUserId()));
                model.addAttribute("title", "Process Completed");
                model.addAttribute("message", "The checklist is created and saved");
            } catch (Exception e) {
                model.addAttribute("title", "Process Failed!");
                model.addAttribute("message", "The checklist failed to be created");
            } finally {
                status.setComplete(); // This ends the session of ChecklistTemplateForm. Used: https://www.logicbig.com/tutorials/spring-framework/spring-web-mvc/spring-model-attribute-with-session.html
                return "message";
            }
        }
    }

    @GetMapping("edit-checklist-title-and-description/{id}")
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public String editchecklisttitleanddescription(@PathVariable(name="id") Long id ,Model model){
        ChecklistTemplate checklistTemplate = authorService.getChecklistTemplateById(id);
        model.addAttribute("title", checklistTemplate.getName());
        model.addAttribute("description", checklistTemplate.getDescription());
        return "checklist/edit/checklist-title-and-description";

    }

//    @PostMapping("edit-checklist-title-and-description")
//    @PreAuthorize("hasRole('ROLE_AUTHOR')")
//    public String


}
