package org.hahlqy.taco.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig  {

    @Autowired
    private DataSource dataSource;


    /**
     * 基于内存的用户存储
     * @return
     */
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails userJack =
//                User.withDefaultPasswordEncoder()
//                        .username("jack")
//                        .password("Aa123456")
//                        .roles("USER")
//                        .build();
//        UserDetails userTom =
//                User.withDefaultPasswordEncoder()
//                        .username("tom")
//                        .password("Aa123456")
//                        .roles("USER")
//                        .build();
//        return new InMemoryUserDetailsManager(userJack,userTom);
//    }


    /**
     * 基于JDBC的用户存储
     * @return
     */
//    @Bean
//    public UserDetailsService userDetailsService() {
//        JdbcUserDetailsManager manager = new JdbcUserDetailsManager();
//        manager.setDataSource(dataSource);
//        if (!manager.userExists("javaboy")) {
//            manager.createUser(User.withUsername("javaboy").password("123").roles("admin").build());
//        }
//        if (!manager.userExists("江南一点雨")) {
//            manager.createUser(User.withUsername("江南一点雨").password("{noop}123").roles("user").build());
//        }
//        return manager;
//    }

}
