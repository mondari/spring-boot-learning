package com.mondari.websocket;

import com.mondari.service.SendService;
import com.mondari.utils.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/websocket")
@Component
public class MyWebSocket {

    private Logger logger = LoggerFactory.getLogger(MyWebSocket.class);

    /**
     * 注入 Service 只能使用这种方式。
     */
    private SendService sendService = SpringUtils.getBean(SendService.class);

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全。
     */
    private static int onlineCount = 0;

    /**
     * 线程安全Set，存放每个客户端对应的MyWebSocket对象。
     */
    private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        // 加入set中
        webSocketSet.add(this);
        // 在线人数加1
        addOnlineCount();
        logger.info("有新连接加入，ID：{}，当前在线人数为{}人", session.getId(), getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        // 从set中删除
        webSocketSet.remove(this);
        // 在线人数减1
        subOnlineCount();
        logger.info("有一连接关闭，ID：{}，当前在线人数为{}人", session.getId(), getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message) {
        String id = session.getId();
        logger.info("客户端 {} 发送消息：{}", id, message);
        try {
            sendService.sendMessage(session, "消息发送成功");
        } catch (IOException e) {
            logger.error("发送消息给客户端 {} 失败 {}", id, e);
        }

        // 群发消息
        sendBatch("客户端 " + session.getId() + " 发送一条消息：" + message);
    }

    /**
     * 连接出错调用的方法
     *
     * @param error
     */
    @OnError
    public void onError(Throwable error) {
        logger.error("客户端 {} 的连接发生错误 {}", session.getId(), error);
    }

    /**
     * 发送消息
     *
     * @param message
     */
    private void sendMessage(String message) {
        this.session.getAsyncRemote().sendText(message);
    }

    /**
     * 群发消息
     *
     * @param message
     */
    private void sendBatch(String message) {
        for (MyWebSocket item : webSocketSet) {
            item.sendMessage(message);
        }
    }

    private static synchronized int getOnlineCount() {
        return onlineCount;
    }

    private static synchronized void addOnlineCount() {
        MyWebSocket.onlineCount++;
    }

    private static synchronized void subOnlineCount() {
        MyWebSocket.onlineCount--;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MyWebSocket that = (MyWebSocket) o;
        return session.equals(that.session);
    }

    @Override
    public int hashCode() {
        return Objects.hash(session);
    }
}
