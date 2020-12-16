package ons.group8.services;

import ons.group8.domain.PersonalChecklist;
import ons.group8.domain.User;

import java.util.List;
import java.util.Optional;

public interface PersonalChecklistService {

    List<PersonalChecklist> findAllPersonalChecklistsByUserEmail(String email);

    Optional<PersonalChecklist> getById(Long pChecklistId);

    boolean isUserAssignedToPersonalChecklist(PersonalChecklist personalChecklist, User user);

    List<Long> getCheckedItemIds(PersonalChecklist personalChecklist);

    void updateCheckedItems(PersonalChecklist personalChecklist, List<Long> checkedItemIds);

    boolean isUserPersonalChecklistAuthor(PersonalChecklist personalChecklist, User user);

    Long getCheckedItemsCount(PersonalChecklist personalChecklist);

    Long getChecklistItemsCount(PersonalChecklist personalChecklist);
}
