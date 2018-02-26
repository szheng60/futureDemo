package com.example.song.futureDemo.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ClientConfig {

    @Bean(name = "endpoint")
    public String endpoint() {
        return "http://localhost:8090";
    }
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
    @Bean
    public ExecutorService executor() {
        return Executors.newFixedThreadPool(4);
    }
}
