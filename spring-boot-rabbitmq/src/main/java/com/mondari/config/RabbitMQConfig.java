package com.mondari.config;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 队列支持负载均衡，当一个队列被多个消费者监听时，发送到该队列的消息会轮流交替发送到这些消费者。
 */
@Configuration
public class RabbitMQConfig {
    public static final String EXCHANGE_DIRECT = "amq.direct";
    public static final String EXCHANGE_TOPIC = "amq.topic";
    public static final String EXCHANGE_FANOUT = "amq.fanout";
    public static final String ROUTING_KEY_DIRECT = "direct";
    public static final String ROUTING_KEY_TOPIC = "topic.#";
    public static final String ROUTING_KEY_TOPIC_FOO = "topic.foo";
    public static final String ROUTING_KEY_TOPIC_BAR = "topic.bar";
    public static final String ROUTING_KEY_FANOUT = "foo.bar";
    public static final String QUEUE_FOO = "foo";
    public static final String QUEUE_BAR = "bar";

    @Bean
    DirectExchange exchangeDirect() {
        return new DirectExchange(EXCHANGE_DIRECT);
    }

    @Bean
    TopicExchange exchangeTopic() {
        return new TopicExchange(EXCHANGE_TOPIC);
    }

    @Bean
    FanoutExchange exchangeFanout() {
        return new FanoutExchange(EXCHANGE_FANOUT);
    }

    @Bean
    Queue queueFoo() {
        return new Queue(QUEUE_FOO, false);
    }

    @Bean
    Queue queueBar() {
        return new Queue(QUEUE_BAR, false);
    }

    /**
     * 凡是发送给 DirectExchange且RoutingKey为“direct”的消息，都会发送到 queueFoo 和 queueBar
     * @return
     */
    @Bean
    Binding bindingQueueFooToDirect() {
        return BindingBuilder.bind(queueFoo()).to(exchangeDirect()).with(ROUTING_KEY_DIRECT);
    }

    @Bean
    Binding bindingQueueBarToDirect() {
        return BindingBuilder.bind(queueBar()).to(exchangeDirect()).with(ROUTING_KEY_DIRECT);
    }

    /**
     * 凡是发送给TopicExchange且RoutingKey以“topic.”开头的消息，都会发送到 queueFoo
     * @return
     */
    @Bean
    Binding bindingQueueFooToTopic() {
        return BindingBuilder.bind(queueFoo()).to(exchangeTopic()).with(ROUTING_KEY_TOPIC);
    }

    @Bean
    Binding bindingQueueFooToFanout() {
        return BindingBuilder.bind(queueFoo()).to(exchangeFanout());
    }

    @Bean
    Binding bindingQueueBarToFanout() {
        return BindingBuilder.bind(queueBar()).to(exchangeFanout());
    }

}
