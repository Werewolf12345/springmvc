package com.alexeiboriskin.study.services;

import com.alexeiboriskin.study.models.User;
import com.alexeiboriskin.study.repositories.UserRepository;
import org.jboss.logging.Logger;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatcher;
import static org.springframework.data.domain.ExampleMatcher.matching;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final Logger logger = Logger.getLogger(this.getClass());

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        User exampleUser = new User();
        exampleUser.setUsername(user.getUsername());
        ExampleMatcher matcher = matching()
                .withIgnoreNullValues()
                .withIgnorePaths("id")
                .withMatcher("username",
                        GenericPropertyMatcher::exact);

        if (!userRepository.findAll(Example.of(exampleUser, matcher)).isEmpty()) {
            logger.info("Username already exists!");
            return null;
        } else {
            return userRepository.save(user);
        }
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> listAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User findByUserName(String username) {
        User exampleUser = new User();
        exampleUser.setUsername(username);
        ExampleMatcher matcher = matching()
                .withIgnoreNullValues()
                .withIgnorePaths("id")
                .withMatcher("username",
                GenericPropertyMatcher::exact);

        return userRepository.findOne(Example.of(exampleUser, matcher)).orElse(null);
    }
}
