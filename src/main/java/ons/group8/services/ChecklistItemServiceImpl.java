package ons.group8.services;

import ons.group8.domain.ChecklistItem;
import ons.group8.repositories.ChecklistItemRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChecklistItemServiceImpl implements ChecklistItemService {

    @Autowired
    private ChecklistItemRepositoryJPA checklistItemRepository;

     public ChecklistItem findByPersonalChecklist_Id(Long personalCheckId){
        return checklistItemRepository.findByPersonalChecklist_Id(personalCheckId);
    }
}
