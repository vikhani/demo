package com.example.demo.client.config;

import com.example.demo.client.exceptions.PokeApiClientException;
import com.example.demo.client.exceptions.PokeApiPokemonNotFound;
import com.example.demo.client.exceptions.PokeApiServerError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

// пункт 3 обработчик ошибок для restTemplate, с которым мы работаем,
// позволяет дополнительно залогировать ошибки,
// обработать их в одном месте (чтобы не писать эту логику везде,
// где используется нужный рестТемплейт и чтобы не засорять логику сервиса)
@Slf4j
public class PokeApiErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        // тут можно расширить логику чем-нибудь вроде
        //         String body = new String(response.getBody().readAllBytes(), StandardCharsets.UTF_8);
        //        if(body.contains("DEBTOR NOT FOUND")) {
        //            return true;
        //        }
        // чтобы обработать ответы со статусом 200, но на самом деле содержащие ошибку
        return !response.getStatusCode().is2xxSuccessful();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        HttpStatusCode status = response.getStatusCode();
        String body = new String(response.getBody().readAllBytes(), StandardCharsets.UTF_8);

        if (status.is4xxClientError()) {
            log.warn("PokeAPI 4xx error: {} - Body: {}", status.value(), body);
            throw new PokeApiPokemonNotFound("PokeAPI request failed with status " + status.value());
        } else if (status.is5xxServerError()) {
            log.error("PokeAPI 5xx error: {} - Body: {}", status.value(), body);
            throw new PokeApiServerError("PokeAPI request failed with status " + status.value());
        } else {
            log.error("Unexpected PokeAPI error: {} - Body: {}", status.value(), body);
            throw new PokeApiClientException("PokeAPI request failed with status " + status.value());
        }

    }
}
