package ons.group8.repositories.checklistRepositories;

import ons.group8.domain.checklist.ChecklistTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChecklistTemplateRepositoryJPA extends JpaRepository<ChecklistTemplate, Long> {
}
