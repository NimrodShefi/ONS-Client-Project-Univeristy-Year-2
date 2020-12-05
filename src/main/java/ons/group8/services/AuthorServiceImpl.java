package ons.group8.services;

import ons.group8.domain.User;
import ons.group8.domain.ChecklistTemplate;
import ons.group8.repositories.UserRepositoryJPA;
import ons.group8.repositories.ChecklistTemplateItemRepositoryJPA;
import ons.group8.repositories.ChecklistTemplateRepositoryJPA;
import ons.group8.repositories.TopicRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final UserRepositoryJPA userRepository;
    private final ChecklistTemplateItemRepositoryJPA checklistTemplateItemRepository;
    private final ChecklistTemplateRepositoryJPA checklistTemplateRepository;
    private final TopicRepositoryJPA topicRepository;

    @Autowired
    public AuthorServiceImpl(UserRepositoryJPA userRepository, ChecklistTemplateItemRepositoryJPA checklistTemplateItemRepository, ChecklistTemplateRepositoryJPA checklistTemplateRepository, TopicRepositoryJPA topicRepository) {
        this.userRepository = userRepository;
        this.checklistTemplateItemRepository = checklistTemplateItemRepository;
        this.checklistTemplateRepository = checklistTemplateRepository;
        this.topicRepository = topicRepository;
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
    public void save(ChecklistCreationEvent data){
        System.out.println(data);
        checklistTemplateRepository.save(new ChecklistTemplate(data.getAuthorId(), data.getTitle(), data.getTitleDescription()));
    }
}
