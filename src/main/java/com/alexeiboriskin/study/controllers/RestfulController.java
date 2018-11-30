package com.alexeiboriskin.study.controllers;

import com.alexeiboriskin.study.models.User;
import com.alexeiboriskin.study.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class RestfulController {
    private final UserService userService;

    public RestfulController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = "application/json")
    public List<User> getAllUsers() {
        return userService.listAllUsers();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public User getUserById(@PathVariable("id") long id) {
        return userService.getUserById(id);
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public User postUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PutMapping(value = "/{id}", produces = "application/json", consumes = "application/json")
    public User putUser(@PathVariable("id") long id, @RequestBody User user) {
        user.setId(id);
        return userService.saveUser(user);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public void deleteUserById(@PathVariable("id") long id) {
        userService.deleteUser(id);
    }
}