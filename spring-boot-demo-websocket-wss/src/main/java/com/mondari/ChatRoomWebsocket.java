package com.mondari;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 多聊天室聊天
 *
 * @author limondar
 */
@Slf4j
@ServerEndpoint(value = "/chatroom/{room}/{username}")
@Component
public class ChatRoomWebsocket {

    /**
     * 在线人数，key为参数room
     */
    private static Map<String, Integer> onlineCount = new ConcurrentHashMap<>();
    /**
     * 在线客户端，Key为SessionId
     */
    private static Map<String, ChatRoomWebsocket> clients = new ConcurrentHashMap<>();

    private Session session;
    private String username;
    private String room;

    @OnOpen
    public void onOpen(@PathParam("username") String username, @PathParam("room") String room,
                       Session session, EndpointConfig conf) {

        this.username = username;
        this.session = session;
        this.room = room;
        addClient(session);

        String message = "用户 " + username + " 加入聊天， 当前在线人数为 " + addOnlineCount(room) + " 人";
        sendBatch(message, room);
        log.info(message);
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        removeClient(session);

        String message = "用户 " + username + " 退出聊天， 当前在线人数为 " + subOnlineCount(room) + " 人";
        sendBatch(message, room);
        log.info("{}，关闭原因：{}", message, reason.getCloseCode().toString());
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        sendBatch(username + "：" + message, room);
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
    private void sendBatch(String message, String room) {
        List<ChatRoomWebsocket> collect = clients.values().stream().filter(chatRoomWebsocket -> room.equals(chatRoomWebsocket.room)).collect(Collectors.toList());
        for (ChatRoomWebsocket item : collect) {
            item.session.getAsyncRemote().sendText(message);
        }
    }

    private synchronized void addClient(Session session) {
        clients.put(session.getId(), this);
    }

    private synchronized void removeClient(Session session) {
        clients.remove(session.getId());
    }

    private static synchronized Integer addOnlineCount(String room) {
        Integer count = onlineCount.getOrDefault(room, 0);
        Integer newCount = ++count;
        onlineCount.put(room, newCount);
        return newCount;
    }

    private static synchronized Integer subOnlineCount(String room) {
        Integer count = onlineCount.getOrDefault(room, 0);
        Integer newCount = --count;
        onlineCount.put(room, newCount);
        return newCount;
    }

}
