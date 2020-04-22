package com.mondari.controller;

import com.mondari.Message;
import com.mondari.mq.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitMQController {

    private final Sender sender;

    @Autowired
    public RabbitMQController(Sender sender) {
        this.sender = sender;
    }

    @PostMapping("/message/direct")
    public void sendMessageToDirect(Message message) {

        sender.sendMessageToDirect(message);
    }

    @PostMapping("/message/topic")
    public void sendMessageToTopic(Message message) {
        sender.sendMessageToTopic(message);
    }

    @PostMapping("/message/fanout")
    public void sendMessageToFanout(Message message) {
        sender.sendMessageToFanout(message);
    }


}
