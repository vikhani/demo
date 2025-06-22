package com.example.demo.client.dto;

import lombok.Data;

import java.util.List;

@Data
public class PokemonApiResponse {
    private int id;
    private String name;
    private int height;
    private List<PokemonType> types;
    private List<PokemonAbilityInfo> abilities;
}
