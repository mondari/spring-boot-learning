package com.mondari;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * <p>
 * 配置资源服务器（如果不配置的话，使用access_token无法访问任何资源）
 * </p>
 * 建议参考自动配置{@link org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerConfiguration}
 *
 * @author limondar
 * @date 2020/3/6
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig
        extends ResourceServerConfigurerAdapter {

    public static final String RESOURCE_ID = "rid";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(RESOURCE_ID);
    }

    /**
     * 配置使用access_token访问资源，注意不要与{@link WebSecurityConfig#configure(HttpSecurity)}配置冲突
     *
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/res/**")// 指定/res开头的由资源管理器处理
                .authorizeRequests()
                .antMatchers("/res/admin/**").hasRole("ADMIN")
                .antMatchers("/res/**").hasRole("USER")
                .anyRequest().authenticated() // 剩余其它接口，认证通过后才能访问
        ;
    }
}
