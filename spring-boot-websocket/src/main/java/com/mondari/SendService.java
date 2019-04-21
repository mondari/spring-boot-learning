package com.mondari;

import javax.websocket.Session;
import java.io.IOException;
import java.util.List;

public interface SendService {

    /**
     * 给多个用户发送消息
     *
     * @param sessionList
     * @param message
     * @throws IOException
     */
    void sendBatch(List<Session> sessionList, String message) throws IOException;

    /**
     * 发送消息
     *
     * @param session
     * @param message
     * @throws IOException
     */
    void sendMessage(Session session, String message) throws IOException;
}