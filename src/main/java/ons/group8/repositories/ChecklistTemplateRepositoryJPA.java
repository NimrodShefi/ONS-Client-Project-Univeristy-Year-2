package ons.group8.repositories;

import ons.group8.domain.ChecklistTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChecklistTemplateRepositoryJPA extends JpaRepository<ChecklistTemplate, Long> {
    ChecklistTemplate getChecklistTemplateById(Long id);

    List<ChecklistTemplate> findAllByAuthor_Email(String authorEmail);
}
