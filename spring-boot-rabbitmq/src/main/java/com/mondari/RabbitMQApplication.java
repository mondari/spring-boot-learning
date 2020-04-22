package com.mondari;

import cn.hutool.core.util.IdUtil;
import com.mondari.mq.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitMQApplication implements CommandLineRunner {

    private final Sender sender;

    @Autowired
    public RabbitMQApplication(Sender sender) {
        this.sender = sender;
    }

    public static void main(String[] args) {
        SpringApplication.run(RabbitMQApplication.class, args);
    }

    @Override
    public void run(String... args) {
        sender.sendMessageToDirect(new Message().setId(IdUtil.fastSimpleUUID()).setContent("Hello, RabbitMQ!"));
    }
}

