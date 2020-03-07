package com.mondari;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * <p>
 * 授权服务器配置
 * </p>
 *
 * @author limondar
 * @date 2020/3/6
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * 认证管理器Bean在SecurityConfig中配置
     */
    @Autowired
    AuthenticationManager authenticationManager;
    /**
     * OAuth2客户端信息在SecurityConfig中配置
     */
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    /**
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                // 允许表单登录认证
                .allowFormAuthenticationForClients()
        ;
    }

    /**
     * 配置OAuth2客户端信息
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                // 设置客户端ID
                .withClient("userClientId")
                // 设置客户端密码（“new BCryptPasswordEncoder().encode("password")” 加密后的密码）
                .secret("$2a$10$2nqy4VBS5veSSWE2YZyW...4ZtkKz57xzv.uT0FXeciktXJfoD7li")// userClientSecret
                // 设置授权方式为password
                // password 授权类型用于首次获取 access_token
                // refresh_token 授权类型用于刷新 access_token
                .authorizedGrantTypes("password", "refresh_token")
                // 资源ID，要和 ResourceServerConfigurerAdapter 配置的资源ID一致，这样相应的资源才会被授权和认证
                .resourceIds(ResourceServerConfig.RESOURCE_ID)
                // 授权域
                .scopes("all")
                .and()
                .withClient("adminClientId")
                .secret("$2a$10$76ocrZZWBc/qyrWzINXcKO8rLcP2YEV8Qu5wUk1lNelfsSGbqfWF6")// adminClientSecret
                .authorizedGrantTypes("password", "refresh_token")
                .resourceIds(ResourceServerConfig.RESOURCE_ID)
                .scopes("all")
        ;
    }

    /**
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 将 access_token 存到 redis 中
        endpoints.tokenStore(new RedisTokenStore(redisConnectionFactory))
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }
}
