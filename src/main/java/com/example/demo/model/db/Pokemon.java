package com.example.demo.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "pokemon")
public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "pokemon_api_id", nullable = false)
    private Long pokemonApiId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "height")
    private Integer height;

    @Column(name = "type", length = 50)
    private String type;

    @Column(name = "primary_ability", length = 100)
    private String primaryAbility;

}