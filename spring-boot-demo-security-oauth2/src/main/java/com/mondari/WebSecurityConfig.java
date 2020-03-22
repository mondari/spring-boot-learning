package com.mondari;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * <p>
 * Security配置
 * </p>
 *
 * @author limondar
 * @date 2020/3/6
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        // super.userDetailsServiceBean() 或 super.userDetailsService() 都可以，推荐后者
        return super.userDetailsService();
    }

    /**
     * 配置AuthenticationManagerBuilder，也就是配置用户、密码和角色，
     * 有了这个才会有AuthenticationManager，否则系统会自动生成一个默认的
     *
     * @param auth
     * @throws Exception
     */
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

}
