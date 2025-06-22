package com.example.demo;

import com.example.demo.service.PokemonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class DemoApplicationTests {

	@Autowired
	private PokemonService pokemonService;

	@Test
	void contextLoads() {
	}

	@Test
	void runRq() {
		pokemonService.collectPokemonInfo();
	}

}
