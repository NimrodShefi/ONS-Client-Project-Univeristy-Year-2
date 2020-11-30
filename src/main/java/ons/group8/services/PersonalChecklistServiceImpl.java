package ons.group8.services;

import ons.group8.domain.PersonalChecklist;
import ons.group8.repositories.PersonalChecklistRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalChecklistServiceImpl implements PersonalChecklistService {

    @Autowired
    private PersonalChecklistRepositoryJPA personalChecklistRepository;

    public PersonalChecklist findByUsers(Integer userId){
        return personalChecklistRepository.findByUsers(userId);
    };
}
