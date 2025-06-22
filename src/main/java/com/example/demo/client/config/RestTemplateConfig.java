package com.example.demo.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

// пункт 1 рестТемплейт вынесенный в бин с подкладыванием логгера запросов и хендлера ошибок
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(
                // для работы LoggingInterceptor, иначе он будет вычитывать стрим ответа и оставлять его пустым
                new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory())
        );

        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new LoggingInterceptor()); // с пункт 2

        restTemplate.setInterceptors(interceptors);
        restTemplate.setErrorHandler(new PokeApiErrorHandler()); // с пункт 3

        return restTemplate;    }
}