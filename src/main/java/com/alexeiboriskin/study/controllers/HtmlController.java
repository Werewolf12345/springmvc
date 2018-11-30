package com.alexeiboriskin.study.controllers;

import com.alexeiboriskin.study.models.User;
import com.alexeiboriskin.study.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HtmlController {
    private final Logger logger = LoggerFactory.getLogger(HtmlController.class);

    private final UserService userService;

    public HtmlController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public ModelAndView loginPage(@RequestParam(value = "error",required = false) String error,
                                  @RequestParam(value = "logout",	required = false) String logout) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid Credentials provided.");
            model.setViewName("signin");
        }

        if (logout != null) {
            model.addObject("message", "Logged out successfully.");
            model.setViewName("signin");
        }

        model.setViewName("signin");
        return model;
    }

    @RequestMapping("user/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "userform";
    }

    @RequestMapping("adminpanel")
    public String adminPanel(Model model) {
        return "adminpanel";
    }

    @RequestMapping(value = "user/{id}", produces = "text/html")
    public String showUser(@PathVariable Long id, Model model) {
        model.addAttribute("userid", id);
        return "user";
    }

    @GetMapping(value = "/users", produces = "text/html")
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

    @PostMapping(value = "user/new")
    public String saveUser(User user) {
        userService.saveUser(user);
        return "redirect:/users";
    }
}
