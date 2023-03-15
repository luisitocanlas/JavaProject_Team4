package com.javapoke;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Trainer {
    //fields
    private String name;
    private Map<Integer, Pokemon> pokemon;

    //constructors
    public Trainer() {
    }

    public Trainer(String name) {
        this.name = name;
    }

    public Trainer(String name, Map<Integer, Pokemon> pokemon) {
        this.name = name;
        this.pokemon = pokemon;
    }

    //accessors
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Integer, Pokemon> getPokemon() {
        return pokemon;
    }

    public void setPokemon(Map<Integer, Pokemon> pokemon) {
        this.pokemon = pokemon;
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "name='" + name + '\'' +
                ", pokemon=" + pokemon +
                '}';
    }
}