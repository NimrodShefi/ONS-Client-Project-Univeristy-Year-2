package ons.group8.repositories;

import ons.group8.domain.ChecklistTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChecklistTemplateRepositoryJPA extends JpaRepository<ChecklistTemplate, Long> {
    ChecklistTemplate findByAuthor(Long userId);
}
