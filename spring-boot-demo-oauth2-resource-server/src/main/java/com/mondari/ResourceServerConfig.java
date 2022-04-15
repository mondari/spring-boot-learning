package com.mondari;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * <p>
 * 资源管理器配置
 * </p>
 *
 * @author limondar
 * @date 2020/3/6
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    public static final String RESOURCE_ID = "oauth2-resource-server";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        // 资源ID可以在配置文件中配置。优先取配置文件中的值
        resources.resourceId(RESOURCE_ID);
    }

    /**
     * 设置 /resource 开头的URI作为资源管理器管理的对象
     *
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/resource").authorizeRequests().anyRequest().authenticated();
    }
}

