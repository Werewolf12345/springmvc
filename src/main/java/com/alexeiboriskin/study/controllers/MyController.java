package com.alexeiboriskin.study.controllers;

import com.alexeiboriskin.study.models.User;
import com.alexeiboriskin.study.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MyController {
    private final Logger logger = LoggerFactory.getLogger(MyController.class);

    private final UserService userService;

    public MyController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("user/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "userform";
    }

    @RequestMapping("user/{id}")
    public String showUser(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "usershow";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("users", userService.listAllUsers());
        return "users";
    }

    @RequestMapping("user/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "userform";
    }

    @RequestMapping("user/delete/{id}")
    public String delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

    @RequestMapping(value = "user/new", method = RequestMethod.POST)
    public String saveUser(User user) {
        userService.saveProduct(user);
        return "redirect:/users";
    }
}
