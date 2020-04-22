package com.mondari.mq;

import com.mondari.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.mondari.config.RabbitMQConfig.QUEUE_BAR;
import static com.mondari.config.RabbitMQConfig.QUEUE_FOO;

@Slf4j
@Component
public class Receiver {

    @RabbitListener(queues = QUEUE_FOO)
    public void receiveMessageForFoo(Message message) {
        log.info("Foo: {}", message.toString());
    }

    @RabbitListener(queues = QUEUE_BAR)
    public void receiveMessageForBar(Message message) {
        log.info("Bar: {}", message.toString());
    }

}

