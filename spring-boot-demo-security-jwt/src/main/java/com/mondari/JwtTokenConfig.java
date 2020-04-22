package com.mondari;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;

/**
 * 添加JWT Tokens功能，需要有 {@link JwtTokenStore} 和 {@link JwtAccessTokenConverter}
 *
 * @author limondar
 */
@Configuration
public class JwtTokenConfig {

    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * {@code JwtAccessTokenConverter} 其内部默认使用 {@code DefaultAccessTokenConverter} 来 convertAccessToken
     *
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        // 对称加密
//        accessTokenConverter.setSigningKey("signingKey");

        // 非对称加密
        KeyPair keyPair = new KeyStoreKeyFactory(new ClassPathResource("keystore.jks"), "password".toCharArray()).getKeyPair("alias");
        accessTokenConverter.setKeyPair(keyPair);

        return accessTokenConverter;
    }

    /**
     * 自定义TokenEnhancer，对 JWT 的载荷进行扩展
     *
     * @return
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new JwtTokenEnhancer();
    }
}
