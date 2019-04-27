package com.mondari;

import com.mondari.model.Person;
import com.mondari.model.Repo;
import com.mondari.service.GitHubService;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.HashMap;
import java.util.List;
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
    public Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate, WebClient client, Retrofit retrofit) {
        return args -> {
            Map<String, Object> map = new HashMap<>();
            map.put("name", "Newton");

            logger.info("----restTemplate----");
            logger.info("GET: {}", restTemplate.getForObject("/hello/{name}", String.class, map));
            logger.info("GET: {}", restTemplate.getForObject("/hello/{name}", String.class, "Einstein"));
            logger.info("POST: {}", restTemplate.postForObject("/person", new Person("Vinci", 50), Person.class));

            logger.info("----WebClient----");
            logger.info("Sync-GET: {}", client.get().uri("hello/{name}", "Plonk").retrieve().bodyToMono(String.class).block());
            logger.info("Sync-POST: {}", client.post().uri("/person").syncBody(new Person("Darwin", 34)).retrieve().bodyToMono(Person.class).block());

            logger.info("----Retrofit----");
            GitHubService gitHubService = retrofit.create(GitHubService.class);
            Call<List<Repo>> call = gitHubService.listRepos("mondari");
            call.enqueue(new Callback<List<Repo>>() {
                @Override
                public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                    logger.info("Async-GET: {}", response.body());
                }

                @Override
                public void onFailure(Call<List<Repo>> call, Throwable t) {
                    logger.info(t.getMessage());
                }
            });
//            logger.info("Sync-GET: {}", call.execute().body());

        };
    }

}

