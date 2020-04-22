package com.mondari.mq;

import com.mondari.Message;
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

    public void sendMessageToDirect(Message message) {
        System.out.println("Sending message to direct exchange");
        rabbitTemplate.convertAndSend(EXCHANGE_DIRECT, ROUTING_KEY_DIRECT, message);
    }

    /**
     * 发布订阅模式
     *
     * @param message
     */
    public void sendMessageToTopic(Message message) {
        System.out.println("Sending message to topic exchange");
        rabbitTemplate.convertAndSend(EXCHANGE_TOPIC, ROUTING_KEY_TOPIC_FOO, message);
        rabbitTemplate.convertAndSend(EXCHANGE_TOPIC, ROUTING_KEY_TOPIC_BAR, message);
    }

    /**
     * 广播模式：发送到 EXCHANGE_FANOUT 的消息，都会被监听该交换机的队列接收。
     *
     * @param message
     */
    public void sendMessageToFanout(Message message) {
        System.out.println("Sending message to fanout exchange");
        // ROUTING_KEY_FANOUT 填任何字符串都会被忽略
        rabbitTemplate.convertAndSend(EXCHANGE_FANOUT, ROUTING_KEY_FANOUT, message);
    }
}
