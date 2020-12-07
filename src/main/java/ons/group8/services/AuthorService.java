package ons.group8.services;

import ons.group8.domain.User;

import java.util.List;


public interface AuthorService {

    List<User> findAll();

    void save(ChecklistCreationEvent checklistForm) throws Exception;

    User findUserByEmail(String email);

    User findUserById(Long userId);
}
