package com.example.demo.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class PokeApiClient {

    private final RestTemplate restTemplate;

    @Value("${pokeapi.base-url:https://pokeapi.co/api/v2/}")
    private String baseUrl;

    public <T> T get(String path, Class<T> responseType) {
        return restTemplate.getForObject(baseUrl + path, responseType);
    }
}