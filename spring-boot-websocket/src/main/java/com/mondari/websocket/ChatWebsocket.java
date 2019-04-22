package com.mondari.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/chat/{username}")
@Controller
public class ChatWebsocket {
    private static Logger logger = LoggerFactory.getLogger(ChatWebsocket.class);

    private static int onlineCount = 0;
    private static Map<String, ChatWebsocket> clients = new ConcurrentHashMap<>();
    private Session session;
    private String username;

    @OnOpen
    public void onOpen(@PathParam("username") String username, Session session) {

        this.username = username;
        this.session = session;

        addOnlineCount();
        clients.put(username, this);
        logger.info("有新连接加入，ID：{}，当前在线人数为{}人", session.getId(), getOnlineCount());
    }

    @OnClose
    public void onClose() {
        clients.remove(username);
        subOnlineCount();
        logger.info("有一连接关闭，ID：{}，当前在线人数为{}人", session.getId(), getOnlineCount());
    }

    @OnMessage
    public void onMessage(String message) {
        sendMessageTo("消息发送成功", username);
        sendMessageAll("客户端 " + session.getId() + " 发送一条消息：" + message);
    }

    @OnError
    public void onError(Throwable error) {
        logger.error("客户端 {} 的连接发生错误 {}", session.getId(), error);
    }

    /**
     * 发送消息给指定的人
     * @param message
     * @param username
     */
    private void sendMessageTo(String message, String username) {
        for (ChatWebsocket item : clients.values()) {
            if (item.username.equals(username)) {
                try {
                    // 这里如果是 session.getAsyncRemote() 会报错
                    item.session.getBasicRemote().sendText(message);
                } catch (Exception e) {
                    logger.error("消息发送出错 {}", e);
                }
            }
        }
    }

    /**
     * 群发消息
     * @param message
     */
    private void sendMessageAll(String message) {
        for (ChatWebsocket item : clients.values()) {
            item.session.getAsyncRemote().sendText(message);
        }
    }

    private static synchronized int getOnlineCount() {
        return onlineCount;
    }

    private static synchronized void addOnlineCount() {
        ChatWebsocket.onlineCount++;
    }

    private static synchronized void subOnlineCount() {
        ChatWebsocket.onlineCount--;
    }

    public static synchronized Map<String, ChatWebsocket> getClients() {
        return clients;
    }
}
