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
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.List;

@SpringBootApplication
public class RestApplication {

    private static final Logger LOG = LoggerFactory.getLogger(RestTemplate.class);
    private static final String ROOT_URI = "http://localhost:8080";
    private static final String GITHUB_API_URL = "https://api.github.com/";

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.rootUri(ROOT_URI).build();
    }

    /**
     * 弃用，这里只演示一下使用方法
     *
     * @return
     */
    @Bean
    public AsyncRestTemplate asyncRestTemplate() {
        return new AsyncRestTemplate();
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
                .baseUrl(GITHUB_API_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate, WebClient client, Retrofit retrofit, AsyncRestTemplate asyncRestTemplate) {
        return args -> {
            String username = "mondari";

            // RestTemplate 请求
            LOG.info("RestTemplate-get-query: {}", restTemplate.getForObject("/hello?name={0}&old={1}", Person.class, "Newton", 23));
            LOG.info("RestTemplate-get-path: {}", restTemplate.getForObject("/hello/{name}/{old}", Person.class, "Einstein", 23));
            LOG.info("RestTemplate-POST: {}", restTemplate.postForObject("/person", new Person("Vinci", 50), Person.class));

            // WebClient 请求
            client.get().uri("hello/{name}/{old}", "Plonk", 32).retrieve().bodyToMono(Person.class)
                    .subscribe(s -> LOG.info("WebClient-GET: {}", s));
            client.post().uri("/person").syncBody(new Person("Darwin", 34)).retrieve().bodyToMono(Person.class)
                    .subscribe(person -> LOG.info("WebClient-POST: {}", person));

            // AsyncRestTemplate 请求
            ListenableFuture<ResponseEntity<String>> entity = asyncRestTemplate
                    .getForEntity("https://api.github.com/users/{user}/repos", String.class, "mondari");
            entity.addCallback(new ListenableFutureCallback<ResponseEntity<String>>() {
                @Override
                public void onFailure(Throwable ex) {
                    LOG.info("AsyncRestTemplate-GET: {}", ex.getMessage());
                }

                @Override
                public void onSuccess(ResponseEntity<String> result) {
                    LOG.info("AsyncRestTemplate-GET: {}", result.getBody());
                }
            });

            // Retrofit 的返回结果支持泛型，非常方便
            GitHubService gitHubService = retrofit.create(GitHubService.class);
            Call<List<Repo>> call = gitHubService.listRepos(username);
            call.enqueue(new Callback<List<Repo>>() {
                @Override
                public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                    LOG.info("Retrofit-GET: {}", response.body());
                }

                @Override
                public void onFailure(Call<List<Repo>> call, Throwable t) {
                    LOG.info("Retrofit-GET: {}", t.getMessage());
                }
            });

        };
    }

}

