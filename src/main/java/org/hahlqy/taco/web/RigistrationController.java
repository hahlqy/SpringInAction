package org.hahlqy.taco.web;

import org.hahlqy.taco.data.mybatis.UserMapper;
import org.hahlqy.taco.vo.RegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RigistrationController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String registerForm(){
        return "register";
    }

    @PostMapping
    public String processRiegistration( RegisterForm form){
        userMapper.save(form.toUser(passwordEncoder));
        return "redirect:/login";
    }

}
