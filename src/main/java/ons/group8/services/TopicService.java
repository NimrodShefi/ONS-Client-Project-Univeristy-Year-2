package ons.group8.services;

import ons.group8.domain.Topic;
import org.springframework.stereotype.Service;


public interface TopicService {

    Topic findByChecklistTemplate_Id(Long checkTempId);
}
