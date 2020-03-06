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
 * 踩坑：ServerEndpoint注解若不指定configurator，那么将不会由Spring容器实例化，也就是说该类中的@Autowired注解将不生效。
 * 指定configurator为SpringConfigurator.class才会纳入到Spring容器中。
 *
 * @author limondar
 */
@Slf4j
@ServerEndpoint(value = "/chat/{username}")
@Component
public class ChatWebsocket {

    /**
     * 在线人数
     */
    private static int onlineCount = 0;
    /**
     * 在线客户端，Key为SessionId
     */
    private static Map<String, ChatWebsocket> clients = new ConcurrentHashMap<>();

    private Session session;
    private String username;

    @OnOpen
    public void onOpen(@PathParam("username") String username, Session session, EndpointConfig conf) {

        this.username = username;
        this.session = session;
        addClient(session);

        String message = "用户 " + username + " 加入聊天， 当前在线人数为 " + addOnlineCount() + " 人";
        sendBatch(message);
        log.info(message);
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        removeClient(session);

        String message = "用户 " + username + " 退出聊天， 当前在线人数为 " + subOnlineCount() + " 人";
        sendBatch(message);
        log.info("{}，关闭原因：{}", message, reason.getCloseCode().toString());
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        sendBatch(username + "：" + message);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户 {} 的连接发生错误 {}", username, error);
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
        for (ChatWebsocket item : clients.values()) {
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
