package ons.group8.repositories.checklistRepositories;

import ons.group8.domain.checklist.ChecklistTemplateItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChecklistTemplateItemRepositoryJPA extends JpaRepository<ChecklistTemplateItem, Long> {
}
