package com.example.demo.client.exceptions;

public class PokeApiPokemonNotFound extends RuntimeException {
    public PokeApiPokemonNotFound(String message) {
        super(message);
    }
}
