package com.mondari.websocket;

import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/chat/{username}/{room}")
@Controller
public class IRCWebsocket {

    private static int onlineCount = 0;
    private static Map<String, IRCWebsocket> clients = new ConcurrentHashMap<>();
    private Session session;
    private String username;
    private String room;

    @OnOpen
    public void onOpen(@PathParam("username") String username, @PathParam("room") String room, Session session) {

        this.username = username;
        this.room = room;
        this.session = session;

        addOnlineCount();
        clients.put(username, this);
        System.out.println("已连接");
    }

    @OnClose
    public void onClose() {
        clients.remove(username);
        subOnlineCount();
    }

    @OnMessage
    public void onMessage(String message) {
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    public void sendMessageTo(String message, String To) {
        // session.getBasicRemote().sendText(message);
        //session.getAsyncRemote().sendText(message);
        for (IRCWebsocket item : clients.values()) {
            if (item.username.equals(To)) {
                try {
                    item.session.getAsyncRemote().sendText(message);
                } catch (Exception e) {
                    System.out.println("信息发送错误" + message);
                }
            }
        }
    }

    public void sendMessageAll(String message) throws IOException {
        for (IRCWebsocket item : clients.values()) {
            item.session.getAsyncRemote().sendText(message);
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        IRCWebsocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        IRCWebsocket.onlineCount--;
    }

    public static synchronized Map<String, IRCWebsocket> getClients() {
        return clients;
    }
}
