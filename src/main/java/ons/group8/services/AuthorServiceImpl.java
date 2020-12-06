package ons.group8.services;

import ons.group8.domain.*;
import ons.group8.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final UserRepositoryJPA userRepository;
    private final ChecklistTemplateRepositoryJPA checklistTemplateRepository;
    private final PersonalChecklistRepositoryJPA personalChecklistRepository;

    @Autowired
    public AuthorServiceImpl(UserRepositoryJPA userRepository, ChecklistTemplateRepositoryJPA checklistTemplateRepository, PersonalChecklistRepositoryJPA personalChecklistRepository) {
        this.userRepository = userRepository;
        this.checklistTemplateRepository = checklistTemplateRepository;
        this.personalChecklistRepository = personalChecklistRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User findUserById(Long userId){
        return userRepository.findUserById(userId);
    }

    @Override
    public void save(ChecklistCreationEvent data) throws Exception {
        System.out.println(data);
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
                personalChecklistRepository.save(new PersonalChecklist(user, checklistTemplate1, dateAssigned));
            }
        } catch (Exception e){
            throw new Exception();
        }
    }

    @Override
    public ChecklistTemplate getChecklistTemplateById(Long id){
        return checklistTemplateRepository.getChecklistTemplateById(id);
    }

    @Override
    public List<PersonalChecklist> getAllByChecklistTemplate(ChecklistTemplate checklistTemplate){
        return personalChecklistRepository.getAllByChecklistTemplate(checklistTemplate);
    }
}
