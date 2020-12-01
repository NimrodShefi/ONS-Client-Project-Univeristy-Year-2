package ons.group8.services;

import ons.group8.domain.PersonalChecklist;

public interface PersonalChecklistService {

    PersonalChecklist findByUser(Integer userId);
}
