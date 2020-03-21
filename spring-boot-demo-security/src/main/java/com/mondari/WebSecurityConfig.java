package com.mondari;

import org.springframework.boot.autoconfigure.security.servlet.WebSecurityEnablerConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.io.PrintWriter;

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
    /**
     * https://spring.io/blog/2017/11/01/spring-security-5-0-0-rc1-released#password-storage-updated
     * Encoded password does not look like BCrypt
     *
     * @return PasswordEncoder
     */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
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
        auth.inMemoryAuthentication()
                .withUser("user")
                .password(passwordEncoder().encode("user")).roles("USER").and()
                .withUser("admin")
                // 密码是：admin
                .password(passwordEncoder().encode("admin")).roles("USER", "ADMIN");
    }

    /**
     * 也可以通过该方式配置用户、密码和角色。只有上面的配置不存在时，这里才会生效
     *
     * @return
     */
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withUsername("another")
                        .password(passwordEncoder().encode("password"))
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
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
                // 2-> 配置表单登录认证
                .formLogin()
                .usernameParameter("username")// 定义登录时用户名的key，默认是username
                .passwordParameter("password")// 定义登陆时密码的key，默认是password
                // 登录成功操作
                .successHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    PrintWriter out = httpServletResponse.getWriter();
                    out.write("login success");
                    out.flush();
                })
                // 登录失败操作
                .failureHandler((httpServletRequest, httpServletResponse, e) -> {
                    httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    PrintWriter out = httpServletResponse.getWriter();
                    out.write("login failure");
                    out.flush();
                })
                .and()
                // 3-> 配置注销
                .logout()
                .logoutUrl("/logout")// 默认值
                .invalidateHttpSession(true)// 默认值
                .clearAuthentication(true)// 默认值
                // 注销成功操作(设置后，上面的 logoutSuccessUrl 设置会被忽略）
                .logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    PrintWriter out = httpServletResponse.getWriter();
                    out.write("logout success");
                    out.flush();
                })
                .and()
                // 4-> 配置 HTTP Basic 认证
                .httpBasic()
                .and()
                // 5->  关闭csrf。踩坑记录：不关闭postman测试登录会失败
                .csrf().disable()
        ;

    }

}
