package ons.group8.repositories;

import ons.group8.domain.PersonalChecklist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalChecklistRepositoryJPA extends JpaRepository<PersonalChecklist, Long> {

    PersonalChecklist findByUsers(Integer userId);
}
