package org.hahlqy.taco.service;

import org.hahlqy.taco.data.mybatis.OrderMapper;
import org.hahlqy.taco.data.mybatis.UserMapper;
import org.hahlqy.taco.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserRepositoryUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findByUserName(username);
        if(user!= null){
            return user;
        }
        //loadUserByUsername绝对不能返回null，如果查不到用户直接抛出UsernameNotFoundException即可
        throw new UsernameNotFoundException("User "+username+" not found");
    }
}
