package com.example.homework_05_testing.services;

import com.example.homework_05_testing.controllers.UserController;
import com.example.homework_05_testing.entities.User;
import com.example.homework_05_testing.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;


    @Test
    void findAll() {
        List<User> users = new ArrayList<>();
        users.add(new User(1L, "John", "john@gmail.com"));
        users.add(new User(2L, "Jane", "jane@gmail.com"));

        // Устанавливаем ожидание, что при вызове метода findAll()
        // у userRepository вернется список users

        when(userRepository.findAll()).thenReturn(users);

        // Вызываем метод контроллера
        Iterable<User> result = userServiceImpl.findAll();
        // Проверяем, что результат совпадает с ожидаемым списком users
        assertEquals(users, result);
        // Проверяем, что метод findAll() у userRepository был вызван один раз
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void findUserByName() {
    }

    @Test
    void findUserById() {
    }

    @Test
    void save() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void delete() {
    }
}