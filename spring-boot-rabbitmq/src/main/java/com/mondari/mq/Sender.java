package com.mondari.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.mondari.config.RabbitMQConfig.*;

@Component
public class Sender {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public Sender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessageToDirect(String message) {
        System.out.println("Sending message to direct");
        rabbitTemplate.convertAndSend(EXCHANGE_DIRECT, ROUTING_KEY_DIRECT, message);
    }

    public void sendMessageToTopic(String message) {
        System.out.println("Sending message to topic");
        rabbitTemplate.convertAndSend(EXCHANGE_TOPIC, ROUTING_KEY_TOPIC_FOO, message);
        rabbitTemplate.convertAndSend(EXCHANGE_TOPIC, ROUTING_KEY_TOPIC_BAR, message);
    }

    /**
     * 发布订阅模式，或者叫广播订阅模式：
     * 发送到 EXCHANGE_FANOUT 的消息，都会被监听该交换机的队列接收。
     *
     * @param message
     */
    public void sendMessageToFanout(String message) {
        System.out.println("Sending message to fanout");
        // ROUTING_KEY_FANOUT 填任何字符串都会被忽略
        rabbitTemplate.convertAndSend(EXCHANGE_FANOUT, ROUTING_KEY_FANOUT, message);
    }
}
