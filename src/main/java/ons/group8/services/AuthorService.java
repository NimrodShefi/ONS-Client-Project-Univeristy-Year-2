package ons.group8.services;

import ons.group8.domain.ChecklistTemplate;
import ons.group8.domain.PersonalChecklist;
import ons.group8.domain.Role;
import ons.group8.domain.User;

import java.util.List;


public interface AuthorService {

    List<User> findUsersByRoles(Role role);

    void save(ChecklistCreationEvent checklistForm) throws Exception;

    ChecklistTemplate getChecklistTemplateById(Long id);

    List<PersonalChecklist> getAllByChecklistTemplate(ChecklistTemplate checklistTemplate);

    List<ChecklistTemplate> getAllByAuthorEmail(String authorEmail);

    List<ChecklistTemplate> findAllChecklistTemplates();

    void cloneChecklistTemplate(ChecklistTemplate checklistTemplate, User author);

}
