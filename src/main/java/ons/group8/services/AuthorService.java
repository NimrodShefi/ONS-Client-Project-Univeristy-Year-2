package ons.group8.services;

import ons.group8.domain.User;

import java.util.List;


public interface AuthorService {
    List<User> getAllUsersName();

    List<User> findAll();
}
