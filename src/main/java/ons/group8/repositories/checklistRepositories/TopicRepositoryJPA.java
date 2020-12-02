package ons.group8.repositories.checklistRepositories;

import ons.group8.domain.checklist.Topic;
import ons.group8.domain.checklist.TopicDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepositoryJPA extends JpaRepository<TopicDB, Long> {
}
