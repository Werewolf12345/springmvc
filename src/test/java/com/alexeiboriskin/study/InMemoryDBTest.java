package com.alexeiboriskin.study;

import com.alexeiboriskin.study.configs.db.DataSourceConfig;
import com.alexeiboriskin.study.models.Role;
import com.alexeiboriskin.study.models.User;
import com.alexeiboriskin.study.repositories.RoleRepository;
import com.alexeiboriskin.study.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatcher;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import static org.junit.Assert.*;
import static org.springframework.data.domain.ExampleMatcher.matching;

@SuppressWarnings("Duplicates")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataSourceConfig.class}, loader =
        AnnotationConfigContextLoader.class)
@Transactional
public class InMemoryDBTest {

    @Resource
    private UserRepository userRepository;

    @Resource
    private RoleRepository roleRepository;

    @Test
    public void givenUserWhenSaveThenGetOk() {
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");
        Role roleGuest = new Role("ROLE_GUEST");

        roleAdmin = roleRepository.save(roleAdmin);
        roleUser = roleRepository.save(roleUser);
        roleGuest = roleRepository.save(roleGuest);

        User user = new User("i_petrov", "Ivan", "Petrov", "IPetrov@gmail" +
                ".com", "pass2", new HashSet<>(Arrays.asList(roleAdmin,
                roleUser, roleGuest)));
        user = userRepository.save(user);
        assertEquals(1L, userRepository.count());

        User user2 = userRepository.getOne(user.getId());
        assertEquals("First name from DB ", "Ivan", user2.getFirstName());

        User exampleUser = new User();
        exampleUser.setUsername("i_petrov");
        ExampleMatcher matcher =
                matching().withIgnoreNullValues().withIgnorePaths("id").withMatcher("username", GenericPropertyMatcher::exact);

        assertNotNull(userRepository.findOne(Example.of(exampleUser, matcher)).orElse(null));

        Collection<? extends GrantedAuthority> authorities =
                user2.getAuthorities();
        assertEquals(3, authorities.size());
        authorities.forEach(a -> assertTrue(a.getAuthority().equals(
                "ROLE_ADMIN") || a.getAuthority().equals("ROLE_USER") || a.getAuthority().equals("ROLE_GUEST")));
    }
}