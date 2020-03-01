package com.mondari;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.PrintWriter;

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
                .password("$2a$10$/tsDoL7LZTBeLvSe4pK0aebam8rUbLq3ERGM2DndqY0DCzsP342zK").roles("USER").and()
                .withUser("admin")
                // 密码是：admin
                .password("$2a$10$yP2.KHlRLUolHRJGJb.afekqyB4bxZEpa06dNB7OXwh55i8eRA5ym").roles("USER", "ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 1-> 请求授权
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/**").hasRole("USER")
                .anyRequest().authenticated() // 剩余其它接口，登录之后就能访问
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
