package com.ambjorn.webrisetask.controller;

import com.ambjorn.webrisetask.dto.UserDTO;
import com.ambjorn.webrisetask.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createUser(@RequestBody UserDTO userDTO) {
        log.info("Принята DTO в createUser: {}", userDTO);
        userService.createUser(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateUser(@RequestBody UserDTO userDTO) {
        log.info("Принята DTO в updateUser: {}", userDTO);
        userService.updateUserInfo(userDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable int id) {
        log.info("Запрос на получение данных пользователя с id {}", id);
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable int id) {
        log.info("Попытка удалить пользователя с id {}", id);
        userService.deleteUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        log.info("Попытка получить список всех пользователей");
        return userService.getAllUsers();
    }
}
