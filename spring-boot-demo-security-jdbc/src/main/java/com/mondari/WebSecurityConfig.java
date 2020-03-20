package com.mondari;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.WebSecurityEnablerConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

/**
 * <p>
 * Security配置
 * </p>
 * 在SpringBoot环境中，{@link EnableWebSecurity}注解可由{@link Configuration}注解替代，
 * 因为在{@link WebSecurityEnablerConfiguration} 中被会自动引入
 *
 * @author limondar
 * @date 2020/3/6
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置用户、密码和角色
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .withDefaultSchema()
                .usersByUsernameQuery(JdbcDaoImpl.DEF_USERS_BY_USERNAME_QUERY)
                .authoritiesByUsernameQuery(JdbcDaoImpl.DEF_AUTHORITIES_BY_USERNAME_QUERY)
                .passwordEncoder(passwordEncoder())
                .withUser("user")
                // 密码是：user
                .password("$2a$10$/tsDoL7LZTBeLvSe4pK0aebam8rUbLq3ERGM2DndqY0DCzsP342zK").roles("USER").and()
                .withUser("admin")
                // 密码是：admin
                .password("$2a$10$yP2.KHlRLUolHRJGJb.afekqyB4bxZEpa06dNB7OXwh55i8eRA5ym").roles("USER", "ADMIN")
        ;
    }

    /**
     * 配置HTTP请求（哪些URL需要哪些权限、表单登录认证还是HTTP Basic认证、注销）
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 1-> 设置哪些接口的请求需要授权
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")// “/admin”开头的接口需要ADMIN角色
                .antMatchers("/**").hasRole("USER")// “/”开头的接口需要USER角色
                .anyRequest().authenticated() // 剩余其它接口，认证通过后才能访问
                .and()
                // 2-> 配置 HTTP Basic 认证
                .httpBasic()
                .and()
                // 3->  关闭csrf。踩坑记录：不关闭postman测试登录会失败
                .csrf().disable()
        ;

    }

}
