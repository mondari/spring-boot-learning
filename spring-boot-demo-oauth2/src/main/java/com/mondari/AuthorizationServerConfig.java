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
 * 建议参考自动配置类：{@link org.springframework.boot.autoconfigure.security.oauth2.authserver.OAuth2AuthorizationServerConfiguration}
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
    public static final String GRANT_TYPE_IMPLICIT = "implicit"; // 简化模式
    public static final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";

    /**
     * 用来配置TokenStore，将 access_token 存到 redis 中
     */
    @Autowired
    RedisConnectionFactory redisConnectionFactory;
    @Autowired
    PasswordEncoder passwordEncoder;
    /**
     * 用来配置OAuth2密码模式的授权方式
     */
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserDetailsService userDetailsService;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                // 允许表单客户端认证（默认不开启），即表单传client_id和client_secret参数，
                // 否则需要请求头传“Authorization: Basic base64("client_id:client_secret")”
                // 比如：假设 client_id和client_secret 分别为 "appId" 和 "appSecret"，
                // 则需在请求头传“Authorization: Basic YXBwSWQ6YXBwU2VjcmV0”
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
                .withClient("appId")
                // 设置客户端Secret
                .secret(passwordEncoder.encode("appSecret"))// userClientSecret
                // 设置OAuth2授权方式
                .authorizedGrantTypes(
                        GRANT_TYPE_CODE,
                        GRANT_TYPE_PASSWORD,
                        GRANT_TYPE_CLIENT_CREDENTIALS,
                        GRANT_TYPE_IMPLICIT,
                        GRANT_TYPE_REFRESH_TOKEN)
                .authorities("ROLE_USER")
                // 如果配置了授权方式为授权码模式，必须设置这个
                .redirectUris("http://mrbird.cc")
                // 资源ID
                .resourceIds(ResourceServerConfig.RESOURCE_ID)
                // 授权域
                .scopes("any")
        ;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
        // 设置前缀为项目名
        tokenStore.setPrefix("security-oauth2:");
        endpoints.tokenStore(tokenStore)
                // 如果配置了授权方式为密码模式，必须设置这个
                .authenticationManager(authenticationManager)
                // 需要加这个，不然在 refresh_token 时会提示 "UserDetailsService is required" 错误
                .userDetailsService(userDetailsService)
        ;

    }
}
