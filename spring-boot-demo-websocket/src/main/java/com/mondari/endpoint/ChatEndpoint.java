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

/**
 * 单聊天室聊天
 * 踩坑：指定configurator为{@link org.springframework.web.socket.server.standard.SpringConfigurator}，ServerEndpoint注解标记的类才会被Spring初始化，
 * 这样该类中的@Autowired注解标记的字段才会被初始化。
 *
 * @author limondar
 */
@Slf4j
@ServerEndpoint(value = "/chat/{userId}", configurator = CustomSpringConfigurator.class)
@Component
public class ChatEndpoint {

    public static final String USER_ID = "userId";
    public static final String DEFAULT_USER_ID = "匿名用户";

    /**
     * 在线人数
     */
    private static int onlineCount = 0;

    @Autowired
    ServiceImpl service;

    @OnOpen
    public void onOpen(@PathParam(USER_ID) String userId, Session session, EndpointConfig conf) {

        // 调用service接口
        service.hello();

        // 保存用户信息
        session.getUserProperties().put(USER_ID, userId);

        // 发送群体消息
        String message = "用户 " + userId + " 加入聊天， 当前在线人数为 " + addOnlineCount() + " 人";
        sendBatch(session, message);
        log.info(message);
    }

    @OnClose
    public void onClose(@PathParam(USER_ID) String userId, Session session, CloseReason reason) {
        String message = "用户 " + userId + " 退出聊天， 当前在线人数为 " + subOnlineCount() + " 人";
        sendBatch(session, message);
        log.info("{}，关闭原因：{}", message, reason.getCloseCode().toString());
    }

    @OnMessage
    public void onMessage(@PathParam(USER_ID) String userId, Session session, String message) {
        sendBatch(session, userId + "：" + message);
    }

    @OnError
    public void onError(@PathParam(USER_ID) String userId, Session session, Throwable error) {
        log.error("用户 {} 的连接发生错误 {}", userId, error);
    }

    /**
     * 发送消息给指定的人
     *
     * @param message
     * @param userId
     */
    @SneakyThrows
    private void sendMessageTo(Session session, String message, String userId) {
        if (session.getUserProperties().getOrDefault(USER_ID, DEFAULT_USER_ID).equals(userId)) {
            session.getBasicRemote().sendText(message);
        }
    }

    /**
     * 群发消息
     *
     * @param session
     * @param message
     */
    @SneakyThrows
    private void sendBatch(Session session, String message) {
        for (Session openSession : session.getOpenSessions()) {
            if (openSession.isOpen()) {
                // 踩坑记录：这里改为异步remote来发送消息，可避免在session关闭的时候发送消息时报“java.io.IOException: UT002002: Channel is closed”异常
                openSession.getAsyncRemote().sendText(message);
            }
        }
    }

    private static synchronized int addOnlineCount() {
        return ++onlineCount;
    }

    private static synchronized int subOnlineCount() {
        return --onlineCount;
    }

}
