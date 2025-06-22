package com.example.demo.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PokemonAbilityInfo {
    private PokemonAbility ability;
    private boolean isHidden;
    private int slot;
}
