package ons.group8.services;

import ons.group8.domain.PersonalChecklist;
import ons.group8.repositories.PersonalChecklistRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalChecklistServiceImpl implements PersonalChecklistService {

    private final PersonalChecklistRepositoryJPA personalChecklistRepository;

    @Autowired
    public PersonalChecklistServiceImpl(PersonalChecklistRepositoryJPA aPersonalChecklistRepository){
        personalChecklistRepository = aPersonalChecklistRepository;
    }

    public PersonalChecklist findByUserId_Id(Long userId){
        return personalChecklistRepository.findByUserId_Id(userId);
    };

}
