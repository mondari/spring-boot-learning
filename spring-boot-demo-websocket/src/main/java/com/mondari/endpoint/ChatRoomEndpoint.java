package com.mondari.endpoint;

import com.mondari.config.CustomSpringConfigurator;
import com.mondari.service.ServiceImpl;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 多聊天室聊天
 *
 * @author limondar
 */
@Slf4j
@ServerEndpoint(value = "/chatroom/{roomId}/{userId}", configurator = CustomSpringConfigurator.class)
@Component
public class ChatRoomEndpoint {

    public static final String ROOM_ID = "roomId";
    public static final String USER_ID = "userId";
    String userInTemplate = "用户 %s 加入聊天室 %s，当前在线人数为 %s 人";
    String userOutTemplate = "用户 %s 退出聊天室 %s，当前在线人数为 %s 人";

    /**
     * 在线人数，key为roomId
     */
    private static Map<String, Integer> onlineCount = new ConcurrentHashMap<>();

    @Autowired
    ServiceImpl service;

    @OnOpen
    public void onOpen(@PathParam(ROOM_ID) String roomId, @PathParam(USER_ID) String userId,
                       Session session, EndpointConfig conf) {

        service.hello();

        Map<String, Object> userProperties = session.getUserProperties();
        userProperties.put(ROOM_ID, roomId);
        userProperties.put(USER_ID, userId);

        String message = String.format(userInTemplate, userId, roomId, addOnlineCount(roomId));
        sendBatch(session, message, roomId);
        log.info(message);
    }

    @OnClose
    public void onClose(@PathParam(ROOM_ID) String roomId, @PathParam(USER_ID) String userId,
                        Session session, CloseReason reason) {
        String message = String.format(userOutTemplate, userId, roomId, subOnlineCount(roomId));
        sendBatch(session, message, roomId);
        log.info("{}，关闭原因：{}", message, reason.getCloseCode().toString());
    }

    @OnMessage
    public void onMessage(@PathParam(ROOM_ID) String roomId, @PathParam(USER_ID) String userId,
                          Session session, String message) {
        sendBatch(session, userId + "：" + message, roomId);
    }

    @OnError
    public void onError(@PathParam(ROOM_ID) String roomId, @PathParam(USER_ID) String userId,
                        Session session, Throwable error) {
        log.error("房间 {} 的用户 {} 的连接发生错误 {}", roomId, userId, error);
    }

    /**
     * 发送消息给指定房间的人
     *
     * @param session
     * @param message
     * @param roomId
     * @param userId
     */
    @SneakyThrows
    private void sendMessageTo(Session session, String message, String roomId, String userId) {
        for (Session openSession : session.getOpenSessions()) {
            Map<String, Object> userProperties = openSession.getUserProperties();
            if (userProperties.getOrDefault(ROOM_ID, ROOM_ID).equals(roomId)
                    && userProperties.getOrDefault(USER_ID, USER_ID).equals(userId)
                    && openSession.isOpen()) {
                openSession.getAsyncRemote().sendText(message);
            }
        }
    }

    /**
     * 群发消息
     *
     * @param session
     * @param message
     */
    @SneakyThrows
    private void sendBatch(Session session, String message, String roomId) {
        for (Session openSession : session.getOpenSessions()) {
            if (openSession.getUserProperties().getOrDefault(ROOM_ID, ROOM_ID).equals(roomId) && openSession.isOpen()) {
                openSession.getAsyncRemote().sendText(message);
            }
        }
    }

    private static synchronized Integer addOnlineCount(String roomId) {
        Integer count = onlineCount.getOrDefault(roomId, 0);
        Integer newCount = ++count;
        onlineCount.put(roomId, newCount);
        return newCount;
    }

    private static synchronized Integer subOnlineCount(String roomId) {
        Integer count = onlineCount.getOrDefault(roomId, 0);
        Integer newCount = --count;
        onlineCount.put(roomId, newCount);
        return newCount;
    }

}
