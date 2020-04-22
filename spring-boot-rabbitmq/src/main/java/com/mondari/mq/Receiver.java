package com.mondari.mq;

import com.mondari.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.mondari.config.RabbitMQConfig.QUEUE_BAR;
import static com.mondari.config.RabbitMQConfig.QUEUE_FOO;

/**
 * 有N个RabbitMQ监听器，就会有N+1个Channel，多出的一个是没有消费者的Channel
 */
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

    /**
     * 监听器也可以酱紫定义
     *
     * @param message
     */
    // @RabbitListener(bindings = @QueueBinding(
    //         value = @Queue(value = QUEUE_BAR, durable = "false"),
    //         exchange = @Exchange(value = RabbitMQConfig.EXCHANGE_DIRECT, type = "direct"),
    //         key = ROUTING_KEY_DIRECT)
    // )
    // public void receiveMessageForBar(Message message) {
    //     log.info("Bar: {}", message.toString());
    // }

}

