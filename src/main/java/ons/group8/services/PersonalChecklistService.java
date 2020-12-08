package ons.group8.services;

import ons.group8.domain.PersonalChecklist;

import java.util.List;

public interface PersonalChecklistService {

    List<PersonalChecklist> findAllPersonalChecklistsByUserEmail(String email);

    PersonalChecklist getById(Long pChecklistId);

}
