package com.alexeiboriskin.study;

import com.alexeiboriskin.study.configs.db.DataSourceConfig;
import com.alexeiboriskin.study.dao.UserRepository;
import com.alexeiboriskin.study.models.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static org.springframework.test.util.AssertionErrors.assertEquals;

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
        User user = new User("John", "Connor", "JConnor@sky.net");
        userRepository.save(user);

        User user2 = userRepository.getOne(1L);
        assertEquals("First name from DB ","John", user2.getFirstName());
    }
}