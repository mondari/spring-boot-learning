package com.mondari;

import com.mondari.service.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @author limondar
 */
@Slf4j
public class ChatHandler extends TextWebSocketHandler {

    /**
     * 在线人数
     */
    private static int onlineCount = 0;

    private static synchronized int addOnlineCount() {
        return ++onlineCount;
    }

    private static synchronized int subOnlineCount() {
        return --onlineCount;
    }

    @Autowired
    private ServiceImpl service;

    /**
     * 相当于 {@link javax.websocket.OnOpen @OnOpen}
     *
     * @param session 会话
     * @throws Exception 异常
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        checkInjectionSupport();
        // TODO 不支持批量发送消息给所有Session?

        String message = "当前在线人数为 " + addOnlineCount() + " 人";
        log.info(message);
        session.sendMessage(new TextMessage(message));
    }

    /**
     * 相当于 {@link javax.websocket.OnClose OnClose}
     *
     * @param session 会话
     * @param status  状态
     * @throws Exception 异常
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String message = "当前在线人数为 " + subOnlineCount() + " 人";
        log.info(message);
    }

    /**
     * 相当于 {@link javax.websocket.OnMessage OnMessage}
     *
     * @param session 会话
     * @param message 消息
     * @throws Exception 异常
     */
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("receive message: {}", message.getPayload());
    }

    /**
     * 相当于 {@link javax.websocket.OnError OnError}
     *
     * @param session   会话
     * @param exception 异常
     * @throws Exception 异常
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("session {} has an error", session.getId(), exception);
    }

    private void checkInjectionSupport() {
        if (service == null) {
            log.warn("@Autowired is not support");
        } else {
            log.info("@Autowired is support");
            service.hello();
        }
    }
}
