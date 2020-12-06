package ons.group8.services;

import ons.group8.domain.ChecklistItem;
import ons.group8.domain.PersonalChecklist;
import ons.group8.repositories.ChecklistItemRepositoryJPA;
import ons.group8.repositories.PersonalChecklistRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalChecklistServiceImpl implements PersonalChecklistService {

    private final PersonalChecklistRepositoryJPA personalChecklistRepository;
    private final ChecklistItemRepositoryJPA checklistItemRepository;

    @Autowired
    public PersonalChecklistServiceImpl(PersonalChecklistRepositoryJPA aPersonalChecklistRepository,
                                    ChecklistItemRepositoryJPA aChecklistItemRepository){
        personalChecklistRepository = aPersonalChecklistRepository;
        checklistItemRepository = aChecklistItemRepository;
    }

    public PersonalChecklist findByUserId_Id(Long userId){
        return personalChecklistRepository.findByUserId_Id(userId);
    };

    public ChecklistItem findByPersonalChecklist_Id(Long personalCheckId){
        return checklistItemRepository.findByPersonalChecklist_Id(personalCheckId);
    }
}
