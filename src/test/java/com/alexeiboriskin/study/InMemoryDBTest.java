package com.alexeiboriskin.study;

import com.alexeiboriskin.study.configs.db.DataSourceConfig;
import com.alexeiboriskin.study.models.User;
import com.alexeiboriskin.study.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatcher;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.data.domain.ExampleMatcher.matching;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = { DataSourceConfig.class },
        loader = AnnotationConfigContextLoader.class)
@Transactional
public class InMemoryDBTest {

    @Resource
    private UserRepository userRepository;

    @Test
    public void givenUserWhenSaveThenGetOk() {
        User user = new User("i_petrov", "Ivan", "Petrov", "IPetrov@gmail.com", "pass2", new String[]{"ADMIN"});
        userRepository.save(user);
        assertEquals(1L, userRepository.count());

        User user2 = userRepository.getOne(1L);
        assertEquals("First name from DB ", "Ivan", user2.getFirstName());

        User exampleUser = new User();
        exampleUser.setUsername("i_petrov");
        ExampleMatcher matcher = matching()
                .withIgnoreNullValues()
                .withIgnorePaths("id")
                .withMatcher("username",
                GenericPropertyMatcher::exact);

        assertNotNull(userRepository.findOne(Example.of(exampleUser, matcher)).orElse(null));
    }
}