package com.example.homework_05_testing.services;

import com.example.homework_05_testing.entities.User;

import java.util.List;

public interface UserService {
    Iterable<User> findAll();

    Iterable<User> findUserByName(String name);

    User findUserById(long id);

    User save(User newUser);

    User updateUser(Long id, User updatedUser);

    void delete(Long id);

    List<User> getUsersWithTotalAmountGreaterThan150();
}
