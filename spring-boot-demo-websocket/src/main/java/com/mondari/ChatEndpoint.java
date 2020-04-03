package com.mondari;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 单聊天室聊天
 * 踩坑：指定configurator为{@link org.springframework.web.socket.server.standard.SpringConfigurator}，ServerEndpoint注解标记的类才会被Spring初始化，
 * 该类中的@Autowired注解将不生效。
 *
 * @author limondar
 */
@Slf4j
@ServerEndpoint(value = "/chat/{userId}")
@Component
public class ChatEndpoint {

    /**
     * 在线人数
     */
    private static int onlineCount = 0;
    /**
     * 在线客户端，Key为SessionId
     */
    private static Map<String, ChatEndpoint> clients = new ConcurrentHashMap<>();

    private Session session;
    private String userId;

    @OnOpen
    public void onOpen(@PathParam("userId") String username, Session session, EndpointConfig conf) {

        this.userId = username;
        this.session = session;
        addClient(session);

        String message = "用户 " + username + " 加入聊天， 当前在线人数为 " + addOnlineCount() + " 人";
        sendBatch(message);
        log.info(message);
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        removeClient(session);

        String message = "用户 " + userId + " 退出聊天， 当前在线人数为 " + subOnlineCount() + " 人";
        sendBatch(message);
        log.info("{}，关闭原因：{}", message, reason.getCloseCode().toString());
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        sendBatch(userId + "：" + message);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户 {} 的连接发生错误 {}", userId, error);
    }

    /**
     * 发送消息给指定的人
     *
     * @param message
     * @param sessionId
     */
    @SneakyThrows
    private void sendMessageTo(String message, String sessionId) {
        if (clients.containsKey(sessionId)) {
            clients.get(sessionId).session.getAsyncRemote().sendText(message);
        }
    }

    /**
     * 群发消息
     *
     * @param message
     */
    @SneakyThrows
    private void sendBatch(String message) {
        for (ChatEndpoint item : clients.values()) {
            item.session.getAsyncRemote().sendText(message);
        }
    }

    private synchronized void addClient(Session session) {
        clients.put(session.getId(), this);
    }

    private synchronized void removeClient(Session session) {
        clients.remove(session.getId());
    }

    private static synchronized int addOnlineCount() {
        return ++onlineCount;
    }

    private static synchronized int subOnlineCount() {
        return --onlineCount;
    }

}
