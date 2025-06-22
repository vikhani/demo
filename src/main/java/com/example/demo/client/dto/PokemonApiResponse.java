package com.example.demo.client.dto;

import lombok.Data;

import java.util.List;

@Data
public class PokemonApiResponse {
    private Long id;
    private String name;
    private int height;
    private List<PokemonType> types;
    private List<PokemonAbilityInfo> abilities;
}
