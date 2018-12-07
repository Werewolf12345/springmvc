package com.alexeiboriskin.study;

import com.alexeiboriskin.study.configs.MvcWebConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MvcWebConfig.class, loader = AnnotationConfigWebContextLoader.class)
@WebAppConfiguration
public class LoginTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void loginWithCorrectCredentialsTest() throws Exception {
        mockMvc.perform(get("/")
                .contentType(MediaType.TEXT_HTML_VALUE)
                .accept(MediaType.TEXT_HTML_VALUE)
        )
                .andExpect(redirectedUrl("http://localhost/signin"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(post("/perform_login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "admin")
                .param("password", "admin")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/adminpanel"));
    }

    @WithMockUser(username="admin", roles = "ADMIN")
    @Test
    public void mainPageRedirectTest() throws Exception {
        mockMvc.perform(get("/")
                .contentType(MediaType.TEXT_HTML_VALUE)
                .accept(MediaType.TEXT_HTML_VALUE)
        )
                .andExpect(forwardedUrl("/adminpanel"))
                .andExpect(status().isOk());
    }

    @Test
    public void loginWithIncorrectCredentialsTest() throws Exception {
        mockMvc.perform(post("/perform_login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "admin")
                .param("password", "wrongPassword")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/signin?error"));
    }
}
