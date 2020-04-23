package com.mondari;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class SecurityApplicationTest {

    @Test
    public void comparePassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "user";
        String encodedPassword = encoder.encode(rawPassword);
        Assert.assertTrue(encoder.matches(rawPassword, encodedPassword));
    }

}
