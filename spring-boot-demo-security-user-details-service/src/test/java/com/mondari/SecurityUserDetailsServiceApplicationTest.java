package com.mondari;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class SecurityUserDetailsServiceApplicationTest {

    @Test
    public void encodedPassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "user";
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println(encodedPassword);
        System.out.println(encoder.matches(rawPassword, encodedPassword));
    }

}
