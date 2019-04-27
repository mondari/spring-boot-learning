package com.mondari;

import com.mondari.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class RestApplication {

    private static Logger logger = LoggerFactory.getLogger(RestTemplate.class);
    private static final String ROOT_URI = "http://localhost:8080";

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.rootUri(ROOT_URI).build();
    }

    /**
     * RestTemplate 默认使用 java.net.HttpURLConnection 来进行发起HTTP请求，
     * 可以切换到支持HTTP2.0的OkHttp3库
     *
     * @return
     */
    @Bean
    public RestTemplate okHttpRestTemplate(RestTemplateBuilder builder) {
        return builder.requestFactory(OkHttp3ClientHttpRequestFactory::new).rootUri(ROOT_URI).build();
    }

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.baseUrl(ROOT_URI).build();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate, WebClient client) {
        return args -> {
            Map<String, Object> map = new HashMap<>();
            map.put("name", "Newton");
            logger.info(restTemplate.getForObject("/hello/{name}", String.class, map));
            logger.info(restTemplate.getForObject("/hello/{name}", String.class, "Einstein"));
            logger.info(client.get().uri("hello/{name}", "Plonk").retrieve().bodyToMono(String.class).block());

            logger.info("{}", restTemplate.postForObject("/person", new Person("Vinci", 50), Person.class));
            logger.info("{}", client.post().uri("/person").syncBody(new Person("Darwin", 34)).retrieve().bodyToMono(Person.class).block());

        };
    }

}

