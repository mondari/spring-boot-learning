package com.mondari;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 配置WebSocket
 *
 * @author limondar
 */
@Configuration
public class WebSocketConfig {
    /**
     * 扫描 {@link javax.websocket.server.ServerEndpointConfig} 和
     * {@link javax.websocket.server.ServerEndpoint} Bean并注册到WebSocket运行时
     *
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}