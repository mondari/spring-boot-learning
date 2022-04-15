package com.mondari;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

class SecurityInMemoryApplicationTest {

    @DisplayName("比较密码")
    @Test
    void comparePassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "user";
        String encodedPassword = encoder.encode(rawPassword);
        Assertions.assertTrue(encoder.matches(rawPassword, encodedPassword));

        PasswordEncoder delegatingPasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        encodedPassword = delegatingPasswordEncoder.encode(rawPassword);
        Assertions.assertTrue(delegatingPasswordEncoder.matches(rawPassword, encodedPassword));
    }

}
