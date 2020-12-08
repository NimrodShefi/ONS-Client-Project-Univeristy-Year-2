package ons.group8.services;

import ons.group8.domain.PersonalChecklist;
import ons.group8.repositories.PersonalChecklistRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonalChecklistServiceImpl implements PersonalChecklistService {

    private final PersonalChecklistRepositoryJPA personalChecklistRepository;

    @Autowired
    public PersonalChecklistServiceImpl(PersonalChecklistRepositoryJPA aPersonalChecklistRepository){
        personalChecklistRepository = aPersonalChecklistRepository;
    }

    public List<PersonalChecklist> findAllPersonalChecklistsByUserEmail(String email){
        return personalChecklistRepository.findAllByUserId_Email(email);
    };

    public PersonalChecklist getById(Long pChecklistId) {
        return personalChecklistRepository.getById(pChecklistId);
    }
}
