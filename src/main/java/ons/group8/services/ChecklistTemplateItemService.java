package ons.group8.services;

import ons.group8.domain.ChecklistTemplateItem;

public interface ChecklistTemplateItemService {
    ChecklistTemplateItem findByTopicId_Id(Long topicId);
}
