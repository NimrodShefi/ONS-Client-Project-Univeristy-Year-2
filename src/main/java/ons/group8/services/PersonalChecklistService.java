package ons.group8.services;

import ons.group8.domain.PersonalChecklist;

import java.util.List;
import java.util.Optional;

public interface PersonalChecklistService {

    List<PersonalChecklist> findAllPersonalChecklistsByUserEmail(String email);

    Optional<PersonalChecklist> getById(Long pChecklistId);

    boolean isUserAssignedToPersonalChecklist(PersonalChecklist personalChecklist, String email);

    List<Long> getCheckedItemIds(PersonalChecklist personalChecklist);

    void updateCheckedItems(PersonalChecklist personalChecklist, List<Long> checkedItemIds);

}
