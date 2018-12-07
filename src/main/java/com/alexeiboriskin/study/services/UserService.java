package com.alexeiboriskin.study.services;

import com.alexeiboriskin.study.models.Role;
import com.alexeiboriskin.study.models.User;
import com.alexeiboriskin.study.repositories.UserRepository;
import org.jboss.logging.Logger;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatcher;
import static org.springframework.data.domain.ExampleMatcher.matching;

@Service
public class UserService {
    private final UserRepository userRepository;
    private RoleService roleService;
    private final Logger logger = Logger.getLogger(this.getClass());

    public UserService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Transactional
    public User saveUser(User user) {
        Set<Role> dbRolesSet = user.getRoles().stream().map(roleService::saveRole).collect(Collectors.toSet());
        user.setRoles(dbRolesSet);

        User exampleUser = new User();
        exampleUser.setUsername(user.getUsername());
        ExampleMatcher matcher = matching()
                .withIgnoreNullValues()
                .withIgnorePaths("id")
                .withMatcher("username",
                        GenericPropertyMatcher::exact);
        User userInDb = userRepository.findOne(Example.of(exampleUser, matcher)).orElse(null);
        if (userInDb == null) {
            user.encryptAndSetPassword(user.getPassword());
        }
        if (userInDb != null && userInDb.getId() != user.getId()) {
            logger.info("Username already exists!");
            return userInDb;
        }
        return userRepository.save(user);

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
