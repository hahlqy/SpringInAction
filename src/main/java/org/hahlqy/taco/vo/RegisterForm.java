package org.hahlqy.taco.vo;

import lombok.Data;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@ToString
public class RegisterForm {

    private String username;

    private String password;

    private String fullname;

    private String street;

    private String city;

    private String state;

    private String zip;

    private String phoneNumber;

    public User toUser(PasswordEncoder encoder){
        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setFullname(fullname);
        user.setStreet(street);
        user.setCity(city);
        user.setState(state);
        user.setZip(zip);
        user.setPhoneNumber(phoneNumber);
        return user;
    }

}
