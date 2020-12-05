package ons.group8.services;

import ons.group8.domain.ChecklistTemplateItem;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public interface ChecklistTemplateItemService {

    ChecklistTemplateItem findByTopicId_Id(Long topicId);


   List<ChecklistTemplateItem> findAllByTopicId_Id(Long topicId);
}
