package ons.group8.repositories;

import ons.group8.domain.ChecklistItem;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChecklistItemRepositoryJPA extends JpaRepository<ChecklistItem, Long> {

    ChecklistItem findByPersonalChecklist_Id(Long personalCheckId);
//
//    @Query(value = "UPDATE checklist_item SET checked=true WHERE id=:checkItemId", nativeQuery = true)
//    void saveCheckedItem(@Param("checklistItemId") Long checkItemId);
}
