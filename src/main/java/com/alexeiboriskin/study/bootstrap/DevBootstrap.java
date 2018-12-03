package com.alexeiboriskin.study.bootstrap;

import com.alexeiboriskin.study.models.User;
import com.alexeiboriskin.study.services.UserService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private UserService userService;

    public DevBootstrap(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        User admin = new User("admin", "John", "Connor", "JConnor@gmail.com",
                "admin", new String[]{"ROLE_USER", "ROLE_ADMIN", "ROLE_GUEST"});
        userService.saveUser(admin);

        User user1 = new User("p_ivanov", "Petr", "Ivanov", "PIvanov@gmail" +
                ".com", "pass1", new String[]{"ROLE_USER"});
        userService.saveUser(user1);

        User user2 = new User("i_petrov", "Ivan", "Petrov", "IPetrov@gmail" +
                ".com", "pass2", new String[]{"ROLE_ADMIN"});
        userService.saveUser(user2);

        User user3 = new User("v_semenovich", "Vasil", "Semenovich", "VSeme" +
                "@gmail.com", "pass3", new String[]{"ROLE_GUEST"});
        userService.saveUser(user3);
    }
}
