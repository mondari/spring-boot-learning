package org.javaboy.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @Author 江南一点雨
 * @Site www.javaboy.org 2019-08-12 23:01
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                // 设置 clientId
                .withClient("clientId")
                // 设置 clientSecret（“new BCryptPasswordEncoder().encode("password")” 加密后的密码）
                .secret("$2a$10$kwLIAqAupvY87OM.O25.Yu1QKEXV1imAv7jWbDaQRFUFWSnSiDEwG")
                // password 授权类型用于首次获取 access_token，refresh_token 授权类型用于刷新 access_token
                .authorizedGrantTypes("password", "refresh_token")
                // access_token 有效时间
                .accessTokenValiditySeconds(1800)
                // 资源ID，要和 ResourceServerConfigurerAdapter 配置的资源ID一致，这样相应的资源才会被授权和认证
                .resourceIds("rid")
                // 域
                .scopes("all")
        ;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 将 access_token 存到 redis 中
        endpoints.tokenStore(new RedisTokenStore(redisConnectionFactory))
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();
    }

    /**
     * 获取 access_token（以admin身份）：
     * POST http://localhost:8080/oauth/token?username=javaboy&password=123&grant_type=password&client_id=clientId&client_secret=123&scope=all
     *
     * 访问资源：
     * GET http://localhost:8080/admin/hello?access_token=fc59a990-3adc-4b7d-9177-40f310487d35
     * GET http://localhost:8080/hello?access_token=fc59a990-3adc-4b7d-9177-40f310487d35
     * GET http://localhost:8080/user/hello?access_token=fc59a990-3adc-4b7d-9177-40f310487d35（admin身份不能访问user身份的资源）
     *
     * 刷新 access_token
     * POST http://localhost:8080/oauth/token?grant_type=refresh_token&refresh_token=f9937e92-8851-4279-af34-42f5c00256f4&client_id=clientId&client_secret=123
     */
}
