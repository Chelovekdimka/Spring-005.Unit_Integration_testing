package com.example.homework_05_testing.services;

import com.example.homework_05_testing.entities.User;
import com.example.homework_05_testing.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import jakarta.transaction.Transactional;

import java.util.List;


@Transactional
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }
    public Iterable<User> findUserByName(String name) {
        return userRepository.findUserByName(name);
    }

    public User findUserById(long id) {
        return userRepository.findUserById(id);
    }

    public User save(User newUser) {
        return userRepository.save(newUser);
    }

    public User updateUser(Long id, User updatedUser) {
        updatedUser.setId(id);
        return userRepository.save(updatedUser);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
    public List<User> getUsersWithTotalAmountGreaterThan150(){
        return userRepository.getUsersWithTotalAmountGreaterThan150();
    }

}
