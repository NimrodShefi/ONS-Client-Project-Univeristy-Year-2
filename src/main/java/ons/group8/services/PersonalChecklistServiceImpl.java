package ons.group8.services;

import ons.group8.domain.ChecklistItem;
import ons.group8.domain.PersonalChecklist;
import ons.group8.repositories.PersonalChecklistRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonalChecklistServiceImpl implements PersonalChecklistService {

    private final PersonalChecklistRepositoryJPA personalChecklistRepository;

    @Autowired
    public PersonalChecklistServiceImpl(PersonalChecklistRepositoryJPA aPersonalChecklistRepository){
        personalChecklistRepository = aPersonalChecklistRepository;
    }

    public List<PersonalChecklist> findAllPersonalChecklistsByUserEmail(String email){
        return personalChecklistRepository.findAllByUserId_Email(email);
    };

    public Optional<PersonalChecklist> getById(Long pChecklistId) {
        return personalChecklistRepository.getById(pChecklistId);
    }

    public boolean isUserAssignedToPersonalChecklist(PersonalChecklist personalChecklist, String email) {
        return personalChecklist.getUser().getEmail().equals(email);
    }

    public List<Long> getCheckedItemIds(PersonalChecklist personalChecklist) {
        return personalChecklist.getChecklistItems()
                .stream()
                .filter(ChecklistItem::isChecked)
                .map(ChecklistItem::getId)
                .collect(Collectors.toList());
    }

    public void updateCheckedItems(PersonalChecklist personalChecklist, List<Long> checkedItemIds) {
        for (Long itemId : checkedItemIds) {
            ChecklistItem item = getChecklistItemWithId(itemId, personalChecklist);
            item.setChecked(true);
        }
        personalChecklistRepository.save(personalChecklist);
    }

    public ChecklistItem getChecklistItemWithId(Long itemId, PersonalChecklist personalChecklist) {
        return personalChecklist.getChecklistItems()
                .stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElse(null);
    }

}
