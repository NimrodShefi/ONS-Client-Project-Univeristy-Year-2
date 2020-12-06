package ons.group8.services;

import ons.group8.domain.ChecklistTemplateItem;
import ons.group8.domain.Topic;
import ons.group8.domain.User;
import ons.group8.domain.ChecklistTemplate;
import ons.group8.repositories.UserRepositoryJPA;
import ons.group8.repositories.ChecklistTemplateItemRepositoryJPA;
import ons.group8.repositories.ChecklistTemplateRepositoryJPA;
import ons.group8.repositories.TopicRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final UserRepositoryJPA userRepository;
    private final ChecklistTemplateRepositoryJPA checklistTemplateRepository;

    @Autowired
    public AuthorServiceImpl(UserRepositoryJPA userRepository, ChecklistTemplateRepositoryJPA checklistTemplateRepository) {
        this.userRepository = userRepository;
        this.checklistTemplateRepository = checklistTemplateRepository;
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
            checklistTemplateRepository.save(checklistTemplate);
        } catch (Exception e){
            throw new Exception();
        }
    }
}
