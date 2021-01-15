package ons.group8.services;

import ons.group8.domain.*;
import ons.group8.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final UserRepositoryJPA userRepository;
    private final ChecklistTemplateRepositoryJPA checklistTemplateRepository;
    private final PersonalChecklistRepositoryJPA personalChecklistRepository;
    private final RoleRepositoryJPA roleRepository;

    @Autowired
    public AuthorServiceImpl(UserRepositoryJPA userRepository, ChecklistTemplateRepositoryJPA checklistTemplateRepository, PersonalChecklistRepositoryJPA personalChecklistRepository, RoleRepositoryJPA roleRepository) {
        this.userRepository = userRepository;
        this.checklistTemplateRepository = checklistTemplateRepository;
        this.personalChecklistRepository = personalChecklistRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<User> findUsersByRoles(String role) {
        return userRepository.findUsersByRoles(roleRepository.getRoleByName(role));
    }

    /**
     * using the data collected, a checklist template is created and also creating a reference of them in personal checklist for all of the users that got assigned the checklist
     * if anything goes wrong in the process, the annotation @Transactional will ensure that it is not saved in the DB
     *
     * @param data - this is the data collected from the controller sent to be saved as a checklist template with the topics and items
     * @throws Exception - should anything go wrong, the server will catch it instead of letting it break the application
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(ChecklistCreationEvent data) throws Exception {
        try {
            ChecklistTemplate checklistTemplate = new ChecklistTemplate(data.getAuthorId(), data.getTitle(), data.getTitleDescription(), data.getTopics());
            for (Topic topic : checklistTemplate.getTopics()) {
                topic.setChecklistTemplate(checklistTemplate);
                for (ChecklistTemplateItem item : topic.getItems()) {
                    item.setTopic(topic);
                }
            }
            ChecklistTemplate checklistTemplate1 = checklistTemplateRepository.save(checklistTemplate);
            LocalDate dateAssigned = LocalDate.now();
            for (User user : data.getAssignedTo()) {
                List<ChecklistItem> items = new ArrayList<>();
                for (Topic topic : checklistTemplate1.getTopics()) {
                    for (ChecklistTemplateItem item : topic.getItems()) {
                        items.add(new ChecklistItem(item, false));
                    }
                }
                PersonalChecklist personalChecklist = new PersonalChecklist(user, checklistTemplate1, dateAssigned, items);
                for (ChecklistItem item : personalChecklist.getChecklistItems()) {
                    item.setPersonalChecklist(personalChecklist);
                }
                personalChecklistRepository.save(personalChecklist);
            }
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Override
    public ChecklistTemplate getChecklistTemplateById(Long id) {
        return checklistTemplateRepository.getChecklistTemplateById(id);
    }

    @Override
    public List<PersonalChecklist> getAllByChecklistTemplate(ChecklistTemplate checklistTemplate) {
        return personalChecklistRepository.getAllByChecklistTemplate(checklistTemplate);
    }

    @Override
    public List<ChecklistTemplate> getAllByAuthorEmail(String authorEmail) {
        return checklistTemplateRepository.findAllByAuthor_Email(authorEmail);
    }

    @Override
    public List<ChecklistTemplate> findAllChecklistTemplates() {
        return checklistTemplateRepository.findAll();
    }

    @Override
    public void cloneChecklistTemplate(ChecklistTemplate template, User author) {
        ChecklistTemplate clonedTemplate = new ChecklistTemplate(template, author);
        checklistTemplateRepository.save(clonedTemplate);
    }
}
