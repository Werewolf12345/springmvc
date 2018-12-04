package com.alexeiboriskin.study.configs;

import com.alexeiboriskin.study.models.User;
import com.alexeiboriskin.study.services.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@ComponentScan("com.alexeiboriskin.study")
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {

    private final MyUserDetailsService myUserDetailsService;

    public SecSecurityConfig(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(User.PASSWORD_ENCODER);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/signin", "/css/**", "/images/**", "/js/**", "/webjars/**").permitAll()
                .antMatchers("/**").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
                .and()
                .httpBasic()
                .and()
                .csrf().disable()
                .formLogin().loginPage("/signin")
                .loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/adminpanel", true)
                .failureUrl("/signin?error")
                .usernameParameter("username").passwordParameter("password")
                .and()
                .logout()
                .logoutUrl("/signout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/signin?logout");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}