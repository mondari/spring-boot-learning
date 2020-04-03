package com.mondari.endpoint;

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
@ServerEndpoint(value = "/chatroom/{roomId}/{userId}")
@Component
public class ChatRoomEndpoint {

    /**
     * 在线人数，key为参数room
     */
    private static Map<String, Integer> onlineCount = new ConcurrentHashMap<>();
    /**
     * 在线客户端，Key为SessionId
     */
    private static Map<String, ChatRoomEndpoint> clients = new ConcurrentHashMap<>();

    private Session session;
    private String userId;
    private String roomId;

    @OnOpen
    public void onOpen(@PathParam("userId") String userId, @PathParam("roomId") String roomId,
                       Session session, EndpointConfig conf) {

        this.userId = userId;
        this.session = session;
        this.roomId = roomId;
        addClient(session);

        String message = "用户 " + userId + " 加入聊天， 当前在线人数为 " + addOnlineCount(roomId) + " 人";
        sendBatch(message, roomId);
        log.info(message);
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        removeClient(session);

        String message = "用户 " + userId + " 退出聊天， 当前在线人数为 " + subOnlineCount(roomId) + " 人";
        sendBatch(message, roomId);
        log.info("{}，关闭原因：{}", message, reason.getCloseCode().toString());
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        sendBatch(userId + "：" + message, roomId);
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
    private void sendBatch(String message, String room) {
        List<ChatRoomEndpoint> collect = clients.values().stream().filter(endpoint -> room.equals(endpoint.roomId)).collect(Collectors.toList());
        for (ChatRoomEndpoint item : collect) {
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
