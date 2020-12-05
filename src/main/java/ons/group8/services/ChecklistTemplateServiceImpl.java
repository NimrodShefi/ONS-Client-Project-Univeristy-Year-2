package ons.group8.services;

import ons.group8.domain.ChecklistTemplate;
import ons.group8.repositories.ChecklistTemplateRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChecklistTemplateServiceImpl implements ChecklistTemplateService{

    @Autowired
    private ChecklistTemplateRepositoryJPA checklistTemplateRepository;

    public ChecklistTemplate findByUserId(Long userId){
        return checklistTemplateRepository.findByUserId(userId);
    }
}
