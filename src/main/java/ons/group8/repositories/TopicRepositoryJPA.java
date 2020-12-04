package ons.group8.repositories;

import ons.group8.domain.TopicDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepositoryJPA extends JpaRepository<TopicDB, Long> {
}
