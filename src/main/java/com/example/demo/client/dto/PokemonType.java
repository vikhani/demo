package com.example.demo.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PokemonType {

    private int slot;
    private TypeDetail type;
}
