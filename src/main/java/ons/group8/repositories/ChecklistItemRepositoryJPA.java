package ons.group8.repositories;

import ons.group8.domain.ChecklistItem;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChecklistItemRepositoryJPA extends JpaRepository<ChecklistItem, Long> {


}
