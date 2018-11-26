package com.alexeiboriskin.study.bootstrap;

import com.alexeiboriskin.study.dao.UserRepository;
import com.alexeiboriskin.study.models.User;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private UserRepository userRepository;

    public DevBootstrap(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        User user1 = new User("Petr", "Ivanov", "PIvanov@gmail.com");
        userRepository.save(user1);

        User user2 = new User("Ivan", "Petrov", "IPetrov@gmail.com");
        userRepository.save(user2);

        User user3 = new User("Vasil", "Semenovich", "VSeme@gmail.com");
        userRepository.save(user3);
    }
}
