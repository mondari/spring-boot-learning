package com.mondari.websocket;

import com.mondari.service.SendService;
import com.mondari.utils.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/websocket")
@Component
public class MyWebSocket {

    private Logger logger = LoggerFactory.getLogger(MyWebSocket.class);

    // 注入 Service 只能使用 getApplicationContext().getBean() 这种方式
    private SendService sendService = SpringUtils.getBean(SendService.class);

    // 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this); //加入set中

        addOnlineCount();   // 在线人数加1
        logger.info("有新连接加入，ID：{}，当前在线人数为{}人", session.getId(), getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        try {
            session.close();
            webSocketSet.remove(this);  //从set中删除
            subOnlineCount();   //在线人数减1
            logger.info("有一连接关闭，ID：{}，当前在线人数为{}人", session.getId(), getOnlineCount());
        } catch (IOException e) {
            logger.error("关闭session时出错 {}", e);
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param session
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(Session session, String message) {
        String id = session.getId();
        logger.info("来自客户端ID为 {} 的消息：{}", id, message);
        try {
            sendService.sendMessage(session, "服务器收到消息");
        } catch (IOException e) {
            logger.error("发送消息给客户端 {} 失败 {}", id, e);
        }

        // 群发消息
        sendBatch("ID为 " + id + " 的客户端发送了消息");
    }

    /**
     * 连接出错调用的方法
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("客户端ID为 {} 的连接发生错误：{}", session.getId(), error);
    }

    /**
     * 发送消息
     * @param message
     * @throws IOException
     */
    private void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 群发消息
     * */
    private void sendBatch(String message) {
        for (MyWebSocket item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                logger.error("群发消息出错 {}", e);
            }
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
}
