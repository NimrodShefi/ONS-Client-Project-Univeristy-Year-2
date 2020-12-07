package ons.group8.repositories;

import ons.group8.domain.ChecklistTemplateItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChecklistTemplateItemRepositoryJPA extends JpaRepository<ChecklistTemplateItem, Long> {

    ChecklistTemplateItem findByTopicId_Id(Long topicId);

    List<ChecklistTemplateItem> findAllByTopicId_Id(Long topicId);
}
