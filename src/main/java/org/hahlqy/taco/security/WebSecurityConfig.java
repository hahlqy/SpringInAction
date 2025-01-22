package org.hahlqy.taco.security;


import org.hahlqy.taco.service.UserRepositoryUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
import java.util.Base64;


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
//            manager.createUser(User.withUsername("javaboy").password(passwordEncoder().encode("123")).roles("admin").build());
//        }
//        if (!manager.userExists("江南一点雨")) {
//            manager.createUser(User.withUsername("江南一点雨").password(passwordEncoder().encode("123")).roles("user").build());
//        }
//        return manager;
//    }


    /**
     * 自定义密码解码器，用于数据库密码与输入密码验证
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
       return  new PasswordEncoder() {
           @Override
           public String encode(CharSequence rawPassword) {
               return Base64.getEncoder().encodeToString(rawPassword.toString().getBytes());
           }

           @Override
           public boolean matches(CharSequence rawPassword, String encodedPassword) {
               return this.encode(rawPassword).equals(encodedPassword);
           }
       };
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((authorize) -> authorize
                        //设置访问权限
                        .requestMatchers("/design").hasAuthority("ROLE_USER")
                        .requestMatchers("/","/**").permitAll()

                ).formLogin(formLogin ->
                        //设置登录配置
                        formLogin
                                .loginPage("/login")
                                .loginProcessingUrl("/authenticate")
                                .usernameParameter("username")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/design")
                                .permitAll()
                ).logout(logout ->
                        //设置登出
                        logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/")
                );
        return http.build();
    }

}
