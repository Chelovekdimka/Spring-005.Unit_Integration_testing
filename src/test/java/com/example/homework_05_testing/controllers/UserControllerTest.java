package com.example.homework_05_testing.controllers;

import com.example.homework_05_testing.entities.User;

import com.example.homework_05_testing.services.UserService;
import com.example.homework_05_testing.services.UserServiceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserServiceImpl userServiceImpl;


    private ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void testGetAll() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(new User(1L, "John", "john@gmail.com"));
        users.add(new User(2L, "Jane", "jane@gmail.com"));

        // Устанавливаем ожидание, что при вызове метода findAll()
        // у userRepository вернется список users

        when(userServiceImpl.findAll()).thenReturn(users);
        mockMvc.perform(get("/users/getAll"))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[0].email").value("john@gmail.com"))

                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Jane"))
                .andExpect(jsonPath("$[1].email").value("jane@gmail.com"));


        // Проверяем, что метод findAll() у userServiceImpl был вызван один раз
        verify(userServiceImpl, times(1)).findAll();
    }


    @Test
    public void testGetByName() throws Exception {
        // Создаем список пользователей
        List<User> users = new ArrayList<>();
        users.add(new User(1L, "John", "john@gmail.com"));
        users.add(new User(2L, "Jane", "jane@gmail.com"));

        // Конфигурируем поведение мока для метода findUserByName в UserService
        when(userServiceImpl.findUserByName("John")).thenReturn(users);

        MvcResult mvcResult = mockMvc.perform(get("/users/getByName?name=John"))
                .andExpect(status().isOk())
                .andReturn();

        // Получаем ответ в виде JSON и преобразуем его в список объектов User
        String responseBody = mvcResult.getResponse().getContentAsString();
        List<User> result = objectMapper.readValue(responseBody, new TypeReference<List<User>>() {
        });

        // Проверяем, что результат совпадает с ожидаемым списком пользователей
        assertEquals(users, result);

        // Проверяем, что метод findUserByName у userService был вызван один раз с аргументом "John"
        verify(userServiceImpl, times(1)).findUserByName("John");

    }

    @Test
    public void testGetById() throws Exception {
        User user = new User(1L, "John", "john@example.com");

        when(userServiceImpl.findUserById(1L)).thenReturn(user);

        MvcResult mvcResult = mockMvc.perform(get("/users/getById/1"))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        User result = objectMapper.readValue(responseBody, User.class);

        assertEquals(user, result);
        verify(userServiceImpl, times(1)).findUserById(1L);
    }

    @Test
    public void testAddUser() throws Exception {
        User newUser = new User(1L, "John", "john@example.com");

        mockMvc.perform(post("/users/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testUpdateUser() throws Exception {
        // Подготовка: создаем пользователя с какими-то начальными данными
        User initialUser = new User(1L, "John", "john@example.com");

        // Подготовка: создаем обновленного пользователя
        User updatedUser = new User(1L, "John Doe", "john.doe@example.com");

        // Подготовка: mock сервиса вернет начального пользователя при поиске
        when(userServiceImpl.findUserById(1L)).thenReturn(initialUser);
        // Подготовка: mock сервиса вернет обновленного пользователя при обновлении
        when(userServiceImpl.updateUser(1L, updatedUser)).thenReturn(updatedUser);

        // Выполнение: отправляем PUT запрос с обновленными данными пользователя
        mockMvc.perform(put("/users/updateUser/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(content().string("Информация о пользователе успешно обновлена"))
                .andDo(print());

        // Проверка: убедимся, что метод updateUser в сервисе был вызван с правильными параметрами
        verify(userServiceImpl, times(1)).updateUser(eq(1L), eq(updatedUser));
    }


    @Test
    public void testUpdateUser_UserNotFound() throws Exception {
        // Подготовка: создаем обновленного пользователя
        User updatedUser = new User(1L, "John Doe", "john.doe@example.com");

//         Подготовка: mock сервиса вернет null при поиске пользователя
        when(userServiceImpl.findUserById(1L)).thenReturn(null);


        // Выполнение: отправляем PUT запрос с обновленными данными пользователя
        mockMvc.perform(put("/users/updateUser/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found"))
                .andDo(print());

        // Проверка: убедимся, что метод updateUser в сервисе не был вызван
        verify(userServiceImpl, never()).updateUser(anyLong(), any(User.class));
    }

    @Test
    public void testDeleteUser() throws Exception {

        User newUser = new User(1L, "John", "john@example.com");
        when(userServiceImpl.findUserById(1L)).thenReturn(newUser);
        mockMvc.perform(delete("/users/deleteUser/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testDeleteUser_UserNotFound() throws Exception {
        // Подготовка: mock сервиса вернет null для поиска пользователя
        when(userServiceImpl.findUserById(1L)).thenReturn(null);

        // Выполнение: отправка DELETE запроса
        mockMvc.perform(delete("/users/deleteUser/1"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}
