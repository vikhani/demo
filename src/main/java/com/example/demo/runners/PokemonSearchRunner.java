package com.example.demo.runners;

import com.example.demo.props.PokemonSearchProperties;
import com.example.demo.service.PokemonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

// пункт 6 раннер, который зовет получение инфы по покемону
// после запуска спринга,
// чтобы не вписывать никуда больше вызов этого сервисного метода
@Slf4j
@Component
@Profile("!test") // к пункт 6 чтобы этот раннер не запускался на тестах
@RequiredArgsConstructor
public class PokemonSearchRunner implements CommandLineRunner {

    private final PokemonService pokemonService;
    private final PokemonSearchProperties properties;

    @Override
    public void run(String... args) {
        log.info("Fetching and saving Pokémon {}", properties);
        pokemonService.collectPokemonInfo();
        log.info("Pokemon search done.");
    }
}