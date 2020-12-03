package ons.group8.services;

import ons.group8.domain.Topic;
import ons.group8.repositories.TopicRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicServiceImpl implements TopicService{

    @Autowired
    private TopicRepositoryJPA topicRepository;

    public Topic findByChecklistTemplate_Id(Long checkTempId){
        return topicRepository.findByChecklistTemplate_Id(checkTempId);
    }
}
