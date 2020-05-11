package com.mondari;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                // 密码是：user
                .password(passwordEncoder().encode("user")).roles("USER").and()
                .withUser("admin")
                // 密码是：admin
                .password(passwordEncoder().encode("admin")).roles("USER", "ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic()
                .and()
                .csrf().disable()
        ;

        UsernamePasswordJsonAuthenticationFilter filter = new UsernamePasswordJsonAuthenticationFilter();
        filter.setAuthenticationSuccessHandler((request, response, authentication) -> {
            response.getWriter().println("login success");
        });
        filter.setAuthenticationFailureHandler((request, response, authentication) -> {
            response.getWriter().println("login failure");
        });
        filter.setAuthenticationManager(authenticationManagerBean());
        http.addFilterAt(filter, UsernamePasswordAuthenticationFilter.class);

    }

}
