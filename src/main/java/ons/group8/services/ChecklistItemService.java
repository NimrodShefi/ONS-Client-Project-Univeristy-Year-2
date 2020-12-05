package ons.group8.services;

import ons.group8.domain.ChecklistItem;

public interface ChecklistItemService {

    ChecklistItem findByPersonalChecklist_Id(Long personalCheckId);
}
