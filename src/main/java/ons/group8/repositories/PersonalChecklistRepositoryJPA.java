package ons.group8.repositories;

import ons.group8.domain.ChecklistTemplate;
import ons.group8.domain.PersonalChecklist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonalChecklistRepositoryJPA extends JpaRepository<PersonalChecklist, Long> {

    List<PersonalChecklist> findAllByUserId_Email(String email);

    List<PersonalChecklist> getAllByChecklistTemplate(ChecklistTemplate checklistTemplate);

    Optional<PersonalChecklist> getById(Long pChecklistId);

}
