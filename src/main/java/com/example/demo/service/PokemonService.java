package com.example.demo.service;

import com.example.demo.client.PokeApiClient;
import com.example.demo.client.dto.PokemonApiResponse;
import com.example.demo.client.exceptions.PokeApiClientException;
import com.example.demo.client.exceptions.PokeApiPokemonNotFound;
import com.example.demo.client.exceptions.PokeApiServerError;
import com.example.demo.mapper.Mapper;
import com.example.demo.model.db.Pokemon;
import com.example.demo.model.db.Report;
import com.example.demo.props.PokemonSearchProperties;
import com.example.demo.repo.PokemonRepository;
import com.example.demo.repo.ReportRepository;
import com.example.demo.service.report_converter.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PokemonService {
    private final PokemonRepository pokemonRepository;
    private final ReportRepository reportRepository;
    private final PokeApiClient pokeApiClient;
    private final PokemonSearchProperties pokemonSearchProperties;
    private final Mapper<PokemonApiResponse, Pokemon> pokemonResponseToPokemonMapper;

    public void collectPokemonInfo() {
        Optional<PokemonApiResponse> pokemon = getPokemonApiResponse();

        if (pokemon.isEmpty()) {
            return;
        }

        var report = ReportService.convertToReport(pokemon.get());
        Pokemon pokemonEntity = pokemonResponseToPokemonMapper.toEntity(pokemon.get());
        List<Report> entityReport = ReportService.toEntities(pokemonEntity.getPokemonApiId(), report);
        reportRepository.saveAll(entityReport);
        if (!pokemonRepository.existsByPokemonApiId(pokemonEntity.getPokemonApiId())) {
            pokemonRepository.save(pokemonEntity);
        } else {
            log.info("Pokemon \"{}\" with pokemonApiId {} is already present.",
                    pokemonEntity.getName(), pokemonEntity.getPokemonApiId());
        }
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
