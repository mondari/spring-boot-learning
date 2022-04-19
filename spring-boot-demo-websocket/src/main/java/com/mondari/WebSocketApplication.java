package com.mondari;

import com.mondari.endpoint.WebSocketClientEndpoint;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import java.net.URI;

/**
 * @author limondar
 */
@RestController
@SpringBootApplication
public class WebSocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebSocketApplication.class, args);
    }

    @GetMapping("hello")
    public String hello() {
        return "hello";
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            WebSocketContainer socketContainer = ContainerProvider.getWebSocketContainer();
            WebSocketClientEndpoint client = new WebSocketClientEndpoint();
            socketContainer.connectToServer(client, new URI("ws://localhost:8443/chat/Picasso"));
            int turn = 0;
            while (turn++ < 5) {
                client.send("send text: " + turn);
            }
            client.close();
        };
    }
}

