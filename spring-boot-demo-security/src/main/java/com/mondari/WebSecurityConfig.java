package com.mondari;

import org.springframework.boot.autoconfigure.security.servlet.WebSecurityEnablerConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

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

    public static final String USERNAME_USER = "user";
    public static final String USERNAME_ADMIN = "admin";
    public static final String ROLE_USER = "USER";
    public static final String ROLE_ADMIN = "ADMIN";

    /**
     * 使用新版的 PasswordEncoder->DelegatingPasswordEncoder 取代 BCryptPasswordEncoder
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
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
        auth.inMemoryAuthentication()
                .withUser(USERNAME_USER)
                .password(passwordEncoder().encode(USERNAME_USER)).roles(ROLE_USER).and()
                .withUser(USERNAME_ADMIN)
                .password(passwordEncoder().encode(USERNAME_ADMIN)).roles(ROLE_USER, ROLE_ADMIN);
    }

    /**
     * 推荐通过该方式配置用户、密码和角色。只有上面的配置不存在时，这里才会生效
     *
     * @return
     */
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername(USERNAME_USER)
                .password(passwordEncoder().encode(USERNAME_USER))
                .roles(ROLE_USER)
                .build();
        UserDetails admin = User.withUsername(USERNAME_ADMIN)
                .password(passwordEncoder().encode(USERNAME_ADMIN))
                .roles(ROLE_USER, ROLE_ADMIN)
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    /**
     * 配置HTTP请求（哪些URL需要哪些权限、表单登录认证还是HTTP Basic认证、注销）
     *
     * @param http
     * @throws Exception
     */
    @SuppressWarnings("AlibabaAvoidCommentBehindStatement")
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 1-> 配置接口权限
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests.antMatchers("/admin/**").hasRole(ROLE_ADMIN) // “/admin”开头的接口需要ADMIN角色
                                .antMatchers("/", "/error", "/webjars/**").permitAll() // 这些接口不需要认证
                                .anyRequest().authenticated() // 其余接口，认证通过才能访问，但不需要授权
                )
                // 2-> 配置表单登录
                .formLogin(formLogin ->
                                formLogin.usernameParameter("username") // 表单登录的用户名参数，默认取 username 参数
                                        .passwordParameter("password") // 表单登录的密码参数，默认取 password 参数
                                        // .loginPage("/login") // 登录页URL，默认是 GET /login。如果用系统自带的登录页，则注释掉该行代码
                                        .loginProcessingUrl("/login") // 处理登录接口URL，默认是 POST /login
                                        // .successForwardUrl("/") // 登录成功跳转URL，该接口需要支持 POST 请求。默认会跳转到登录前地址
                                        .failureUrl("/login?error") // 登录失败跳转URL，默认是 /login?error
                        // 登录成功操作(会覆盖上面的 successForwardUrl 设置。适合前后端分离场景）
                        // .successHandler((httpServletRequest, httpServletResponse, authentication) -> {
                        //     httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
                        //     PrintWriter out = httpServletResponse.getWriter();
                        //     out.write("login success");
                        //     out.flush();
                        // })
                        // // 登录失败操作(会覆盖上面的 failureUrl 设置。适合前后端分离场景）
                        // .failureHandler((httpServletRequest, httpServletResponse, e) -> {
                        //     httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
                        //     PrintWriter out = httpServletResponse.getWriter();
                        //     out.write("login failure");
                        //     out.flush();
                        // })
                )
                // 3-> 配置注销
                .logout(logout ->
                                logout.logoutUrl("/logout")// 注销接口URL默认值
                                        // .logoutSuccessUrl("/")// 注销成功跳转URL，默认是 /login?logout
                                        .invalidateHttpSession(true)// 默认值
                                        .clearAuthentication(true)// 默认值
                        // 注销成功操作(会覆盖上面的 logoutSuccessUrl 设置。适合前后端分离场景）
                        // .logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> {
                        //     httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
                        //     PrintWriter out = httpServletResponse.getWriter();
                        //     out.write("logout success");
                        //     out.flush();
                        // })
                )
                // 4-> 配置 HTTP Basic 认证
                .httpBasic()
                .and()
                // 5->  关闭csrf。踩坑记录：不关闭的话使用postman测试需要添加csrf参数，否则出错
                .csrf().disable()
                // 6-> 配置session管理
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
        ;

    }

}
