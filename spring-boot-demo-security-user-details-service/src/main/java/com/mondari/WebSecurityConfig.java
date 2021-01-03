package com.mondari;

import org.springframework.boot.autoconfigure.security.servlet.WebSecurityEnablerConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // 下面这段代码不要也行，只要自定义UserDetailsService是bean即可，Security会自动从容器中找到所需的Bean去配置
    // @Autowired
    // UserDetailsService customUserDetailsServiceImpl;
    //
    // @Override
    // protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    //     auth.userDetailsService(customUserDetailsServiceImpl).passwordEncoder(passwordEncoder());
    // }
    // 上面这段代码不要也行

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
                // 4-> 配置表单登录
                .formLogin()
        ;

    }

}
