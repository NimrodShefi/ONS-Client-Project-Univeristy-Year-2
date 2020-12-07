package ons.group8.repositories;

import ons.group8.domain.ChecklistTemplate;
import ons.group8.domain.ChecklistItem;
import ons.group8.domain.PersonalChecklist;
import ons.group8.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonalChecklistRepositoryJPA extends JpaRepository<PersonalChecklist, Long> {

    PersonalChecklist findByUserId_Id(Long userId);

    List<PersonalChecklist> getAllByChecklistTemplate(ChecklistTemplate checklistTemplate);

}
