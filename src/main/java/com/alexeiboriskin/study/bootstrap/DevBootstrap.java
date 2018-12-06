package com.alexeiboriskin.study.bootstrap;

import com.alexeiboriskin.study.models.Role;
import com.alexeiboriskin.study.models.User;
import com.alexeiboriskin.study.repositories.RoleRepository;
import com.alexeiboriskin.study.services.UserService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {
    private UserService userService;
    private RoleRepository roleRepository;

    public DevBootstrap(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

   // @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");
        Role roleGuest = new Role("ROLE_GUEST");

        roleAdmin = roleRepository.save(roleAdmin);
        roleUser = roleRepository.save(roleUser);
        roleGuest = roleRepository.save(roleGuest);

        User admin = new User("admin", "John", "Connor", "JConnor@gmail.com",
                "admin", new HashSet<>(Arrays.asList(roleAdmin, roleUser, roleGuest)));

        User user1 = new User("p_ivanov", "Petr", "Ivanov", "PIvanov@gmail" +
                ".com", "pass1", new HashSet<>(Collections.singletonList(roleAdmin)));

        User user2 = new User("i_petrov", "Ivan", "Petrov", "IPetrov@gmail" +
                ".com", "pass2", new HashSet<>(Collections.singletonList(roleUser)));

        User user3 = new User("v_semenovich", "Vasil", "Semenovich", "VSeme" +
                "@gmail.com", "pass3", new HashSet<>(Arrays.asList(roleGuest, roleUser)));

        userService.saveUser(admin);
        userService.saveUser(user1);
        userService.saveUser(user2);
        userService.saveUser(user3);
    }
}
