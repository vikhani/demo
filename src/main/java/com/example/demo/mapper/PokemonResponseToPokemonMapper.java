package com.example.demo.mapper;

import com.example.demo.client.dto.PokemonAbilityInfo;
import com.example.demo.client.dto.PokemonApiResponse;
import com.example.demo.client.dto.PokemonType;
import com.example.demo.model.db.Pokemon;
import org.springframework.stereotype.Component;

//пункт 5 реализуем маппер отдельно от сервисной логики
@Component
public class PokemonResponseToPokemonMapper implements Mapper<PokemonApiResponse, Pokemon> {
    @Override
    public Pokemon toEntity(PokemonApiResponse dto) {
        PokemonType pokemonType = dto.getTypes().get(0);
        PokemonAbilityInfo abilityInfo = dto.getAbilities().get(0);

        return new Pokemon()
                .setPokemonApiId(dto.getId())
                .setName(dto.getName())
                .setType(pokemonType == null ? null : pokemonType.getType().getName())
                .setHeight(dto.getHeight())
                .setPrimaryAbility(abilityInfo == null ? null : abilityInfo.getAbility().getName());
    }
}
