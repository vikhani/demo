package com.example.demo.service;

import com.example.demo.client.PokeApiClient;
import com.example.demo.client.dto.PokemonApiResponse;
import com.example.demo.client.exceptions.PokeApiClientException;
import com.example.demo.client.exceptions.PokeApiPokemonNotFound;
import com.example.demo.client.exceptions.PokeApiServerError;
import com.example.demo.mapper.Mapper;
import com.example.demo.model.db.Pokemon;
import com.example.demo.props.PokemonSearchProperties;
import com.example.demo.repo.PokemonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PokemonService {
    private final PokemonRepository pokemonRepository;
    private final PokeApiClient pokeApiClient;
    private final PokemonSearchProperties pokemonSearchProperties;
    private final Mapper<PokemonApiResponse, Pokemon> pokemonResponseToPokemonMapper;

    public void collectPokemonInfo() {
        Optional<PokemonApiResponse> pokemon = getPokemonApiResponse();

        if (pokemon.isEmpty()) {
            return;
        }

        Pokemon pokemonEntity = pokemonResponseToPokemonMapper.toEntity(pokemon.get());

        pokemonRepository.save(pokemonEntity);
    }

    private Optional<PokemonApiResponse> getPokemonApiResponse() {
        try {
            return Optional.ofNullable(
                    pokeApiClient.get(
                            "pokemon/" + pokemonSearchProperties.getName(),
                            PokemonApiResponse.class
                    )
            );
        } catch (PokeApiClientException | PokeApiServerError | PokeApiPokemonNotFound e) {
            // тут отлавливаем наши кастомные ошибки для примера,
            // возможно программа должна падать при ошибке и тогда их ловить не нужно,
            // а может мы бы хотели как-то еще обработать ошибку, например сделать запрос еще раз
            return Optional.empty();
        }
    }
}
