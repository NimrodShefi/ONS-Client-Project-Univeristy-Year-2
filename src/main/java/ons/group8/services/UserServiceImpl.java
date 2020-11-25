package ons.group8.services;

import lombok.AllArgsConstructor;
import ons.group8.domain.User;
import ons.group8.repositories.UserRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService{

    private UserRepositoryJPA userRepository;

    @Autowired
    public UserServiceImpl(UserRepositoryJPA userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void createUser(User user) {
        userRepository.save(user);
    }
}
