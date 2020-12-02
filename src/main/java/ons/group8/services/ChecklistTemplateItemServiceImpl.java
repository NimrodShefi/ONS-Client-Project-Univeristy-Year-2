package ons.group8.services;

import ons.group8.domain.ChecklistTemplateItem;
import ons.group8.repositories.ChecklistItemRepositoryJPA;
import ons.group8.repositories.ChecklistTemplateItemRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChecklistTemplateItemServiceImpl implements ChecklistTemplateItemService{

    @Autowired
    private ChecklistTemplateItemRepositoryJPA  checklistTemplateItemRepository;



    @Override
    public ChecklistTemplateItem findByTopicId_Id(Long topicId){
        return checklistTemplateItemRepository.findByTopicId_Id(topicId);
    }

    @Override
    public List<ChecklistTemplateItem> findAllByTopicId_Id(Long topicId){
        return checklistTemplateItemRepository.findAllByTopicId_Id(topicId);
    }
}
