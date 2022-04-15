package com.mondari;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class AuthorizationServerApplicationTest {

    @Test
    void encodedPassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "user";
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println(encodedPassword);
        System.out.println(encoder.matches(rawPassword, encodedPassword));
    }

}
