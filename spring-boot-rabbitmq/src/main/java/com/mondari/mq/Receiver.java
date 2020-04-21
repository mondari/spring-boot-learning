package com.mondari.mq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.mondari.config.RabbitMQConfig.QUEUE_BAR;
import static com.mondari.config.RabbitMQConfig.QUEUE_FOO;

/**
 * 有N个RabbitMQ监听器，就会有N+1个Channel，多出的一个是没有消费者的Channel
 */
@Component
public class Receiver {

    @RabbitListener(queues = QUEUE_FOO)
    public void receiveMessageForFoo(String message) {
        System.out.println("Queue foo received: <" + message + ">");
    }

    @RabbitListener(queues = QUEUE_BAR)
    public void receiveMessageForBar(String message) {
        System.out.println("Queue bar received: <" + message + ">");
    }

}

