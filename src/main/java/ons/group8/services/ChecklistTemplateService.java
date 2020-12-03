package ons.group8.services;

import ons.group8.domain.ChecklistTemplate;

public interface ChecklistTemplateService {

    ChecklistTemplate findByUserId(Long userId);
}
