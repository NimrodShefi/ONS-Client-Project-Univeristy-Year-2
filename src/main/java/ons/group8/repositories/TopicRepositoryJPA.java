package ons.group8.repositories;

import ons.group8.domain.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepositoryJPA extends JpaRepository<Topic, Long> {

    Topic findByChecklistTemplate_Id(Long checkTempId);
}
