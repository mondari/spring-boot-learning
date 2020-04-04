package com.mondari.config;

import com.mondari.ChatHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 * 配置WebSocket
 *
 * @author limondar
 */
@EnableWebSocket
@Configuration
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatHandler(), "/chat/{userId}")
                .addInterceptors(new HttpSessionHandshakeInterceptor())
                .setAllowedOrigins("*")
        ;
    }

    @Bean
    public WebSocketHandler chatHandler() {
        return new ChatHandler();
    }
}