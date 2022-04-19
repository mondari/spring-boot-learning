package com.mondari.endpoint;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;

@Slf4j
@Component
@ClientEndpoint
public class WebSocketClientEndpoint {
    private Session session;

    @OnOpen
    public void onOpen(Session session, EndpointConfig conf) {
        log.info("connect to server success");
        this.session = session;
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        log.info("session {} close for: {}", session.getId(), reason.getCloseCode().toString());
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        log.info("session {} send message: {}", session.getId(), message);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("session {} has an error", session.getId(), error);
    }

    @SneakyThrows
    public void send(Object message) {
        session.getBasicRemote().sendObject(message);
    }

    @SneakyThrows
    public void close() {
        if (session.isOpen()) {
            session.close();
        }
    }
}
