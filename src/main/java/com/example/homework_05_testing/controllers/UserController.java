package com.example.homework_05_testing.controllers;

import com.example.homework_05_testing.entities.User;
import com.example.homework_05_testing.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }


    @GetMapping("/getAll")
    public Iterable<User> getAll() {
        return userServiceImpl.findAll();
    }

    //http://localhost:8080/users/getByName?name=John%20Doe
    @GetMapping("/getByName")
    public Iterable<User> getByName(@RequestParam String name) {
        return userServiceImpl.findUserByName(name);
    }

    @GetMapping("/getById/{id}")
    public User getById(@PathVariable long id) {
        return userServiceImpl.findUserById(id);
    }

    @PostMapping("/addUser")
    public User addUser(@RequestBody User newUser) {
        return userServiceImpl.save(newUser);
    }


    @PutMapping("/updateUser/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User existingUser = userServiceImpl.findUserById(id);
        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }
        User updatedUserInfo = userServiceImpl.updateUser(id, updatedUser);
        return ResponseEntity.ok("Информация о пользователе успешно обновлена");
}


    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        User user = userServiceImpl.findUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();  // Возвращаем статус 404, если пользователь не найден
        }
        userServiceImpl.delete(id);
        return ResponseEntity.ok("User deleted successfully");
    }


    @GetMapping("/getUsersWithTotalAmountGreaterThan150")
    public List<User> getUsersWithTotalAmountGreaterThan150 (){
        return userServiceImpl.getUsersWithTotalAmountGreaterThan150();
    }
}
