package com.mondari;

import lombok.extern.slf4j.Slf4j;
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

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String message = "当前在线人数为 " + addOnlineCount() + " 人";
        log.info(message);
        session.sendMessage(new TextMessage(message));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String message = "当前在线人数为 " + subOnlineCount() + " 人";
        log.info(message);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    }


}
