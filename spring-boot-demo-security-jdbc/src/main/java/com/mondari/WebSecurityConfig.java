package com.mondari;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.WebSecurityEnablerConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

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
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
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
                .dataSource(dataSource)// 必须要设置
                .withDefaultSchema()// 使用默认的建表语句建表
                .usersByUsernameQuery(JdbcDaoImpl.DEF_USERS_BY_USERNAME_QUERY)// 默认值
                .authoritiesByUsernameQuery(JdbcDaoImpl.DEF_AUTHORITIES_BY_USERNAME_QUERY)// 默认值
                .withUser("user")
                .password(passwordEncoder().encode("user")).roles("USER").and()
                .withUser("admin")
                .password(passwordEncoder().encode("admin")).roles("USER", "ADMIN")
        ;
    }

    /**
     * 也可以使用以下方式配置用户、密码和角色。（两种配置只能存在一种）
     * 注意：需要先创建数据库表，或在配置文件中配置默认建表语句“spring.datasource.schema:classpath:org/springframework/security/core/userdetails/jdbc/users.ddl”
     *
     * @param dataSource
     * @return
     */
    // @Bean
    // UserDetailsManager users(DataSource dataSource) {
    //     UserDetails user = User.builder()
    //             .username("user")
    //             .password(passwordEncoder().encode("user"))
    //             .roles("USER")
    //             .build();
    //     UserDetails admin = User.builder()
    //             .username("admin")
    //             .password(passwordEncoder().encode("admin"))
    //             .roles("USER", "ADMIN")
    //             .build();
    //     JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
    //     jdbcUserDetailsManager.createUser(user);
    //     jdbcUserDetailsManager.createUser(admin);
    //     return jdbcUserDetailsManager;
    // }

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
                // 3->  关闭csrf
                .csrf().disable()
        ;

    }

}
