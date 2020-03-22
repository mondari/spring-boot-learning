package com.mondari;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    public static final String GRANT_TYPE_CODE = "authorization_code"; // 授权码模式
    public static final String GRANT_TYPE_PASSWORD = "password"; // 密码模式
    public static final String GRANT_TYPE_CLIENT_CREDENTIALS = "client_credentials"; // 客户端模式
    public static final String GRANT_TYPE_IMPLICIT = "implicit"; // 隐式模式
    public static final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";
    /**
     * 认证管理器Bean在WebSecurityConfig中配置
     */
    @Autowired
    AuthenticationManager authenticationManager;
    /**
     * OAuth2客户端信息在WebSecurityConfig中配置
     */
    @Autowired
    UserDetailsService userDetailsService;
    /**
     * 用来配置TokenStore
     */
    @Autowired
    RedisConnectionFactory redisConnectionFactory;
    /**
     * 用来加密密码
     */
    @Autowired
    PasswordEncoder passwordEncoder;

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
        /**
         * 这里设置了两种授权方式，一种是password，一种是client_credentials
         */
        clients.inMemory()
                // 设置客户端ID
                .withClient("clientId")
                // 设置客户端Secret
                .secret(passwordEncoder.encode("123456"))// userClientSecret
                // 设置OAuth2授权方式为client_credentials
                .authorizedGrantTypes(GRANT_TYPE_CLIENT_CREDENTIALS, GRANT_TYPE_REFRESH_TOKEN)
                // 资源ID，要和 ResourceServerConfigurerAdapter 配置的资源ID一致，这样相应的资源才会被授权和认证
                .resourceIds(ResourceServerConfig.RESOURCE_ID)
                // 授权域
                .scopes("any")
                .and()
                .withClient("passwordClient")
                .secret(passwordEncoder.encode("passwordSecret"))// adminClientSecret
                // 设置OAuth2授权方式为password
                .authorizedGrantTypes(GRANT_TYPE_PASSWORD, GRANT_TYPE_REFRESH_TOKEN)
                .resourceIds(ResourceServerConfig.RESOURCE_ID)
                .scopes("any")
        ;
    }

    /**
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 将 access_token 存到 redis 中
        RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
        // 设置前缀为项目名
        tokenStore.setPrefix("security-oauth2:");
        endpoints.tokenStore(tokenStore)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }
}
