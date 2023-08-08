package com.example.homework_05_testing.repositories;

import com.example.homework_05_testing.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.ClassBasedNavigableIterableAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Test
    void getUsersWithTotalAmountGreaterThan150() {
        List<User> listOfUsers = userRepository.getUsersWithTotalAmountGreaterThan150();
        assertNotNull(listOfUsers);
        assertEquals(2,listOfUsers.size());
        assertEquals("Jane Smith", listOfUsers.get(0).getName());
    }
    @Test
//    @Sql("/data.sql") // Импортируем тестовые данные из файла data.sql
    public void testUpdateUserInfo() {
        // Подготовка: ID пользователя, данные для обновления
        Long userId = 1L;
        String newName = "Jane Doe";
        String newEmail = "jane.doe@gmail.com";

        // Выполнение: обновляем информацию о пользователе
        userRepository.updateUserInfo(newName, newEmail, userId);

        // Проверка: убеждаемся, что информация была обновлена
        User updatedUser = userRepository.findById(userId).orElse(null);
        if (updatedUser != null) {
            assertEquals(newName, updatedUser.getName());
            assertEquals(newEmail, updatedUser.getEmail());
        } else {
            fail("User was not updated");
        }
    }
}



