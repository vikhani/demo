package com.example.demo.client.exceptions;

public class PokeApiServerError extends RuntimeException {
    public PokeApiServerError(String message) {
        super(message);
    }
}
