package ons.group8.repositories;

import ons.group8.domain.ChecklistTemplateItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChecklistTemplateItemRepositoryJPA extends JpaRepository<ChecklistTemplateItem, Long> {
}
