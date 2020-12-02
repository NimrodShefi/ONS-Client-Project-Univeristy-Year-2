package ons.group8.services;

import ons.group8.domain.User;
import ons.group8.domain.checklist.ChecklistTemplate;
import ons.group8.domain.checklist.ChecklistTemplateItem;
import ons.group8.domain.checklist.Topic;
import ons.group8.domain.checklist.TopicDB;
import ons.group8.repositories.UserRepositoryJPA;
import ons.group8.repositories.checklistRepositories.ChecklistTemplateItemRepositoryJPA;
import ons.group8.repositories.checklistRepositories.ChecklistTemplateRepositoryJPA;
import ons.group8.repositories.checklistRepositories.TopicRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        // save the checklist template and then use the Id that is returned to save the next part of the data which are the topics
        System.out.println(data);
        Long checklistTemplateId = checklistTemplateRepository.save(new ChecklistTemplate(data.getAuthorId(), data.getTitle(), data.getTitleDescription())).getId();
        /*
         because we can have multiple topics with each topic also capable of having multiple items 2 for loops are needed
         first for loop is to save the topic and get Id returned so that it can be used to save the items
         after the item was saved, we save the list of items from topic into the db
         and reference the topic id we received from the saving of topic
         */
        for (Topic topic: data.getTopics()) {
            topic.setId(topicRepository.save(new TopicDB(checklistTemplateId, topic.getTopicTitle(), topic.getTopicDescription())).getId());
            for (String item : topic.getItems()) {
                checklistTemplateItemRepository.save(new ChecklistTemplateItem(topic.getId(), item));
            }
        }
    }
}
