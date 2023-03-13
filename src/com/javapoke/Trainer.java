package com.javapoke;


import java.util.ArrayList;
import java.util.List;

public class Trainer {
    //fields
    String name;
    List<Pokemon> pokemon;

    //constructors
    public Trainer(String name) {
        this.name = name;
    }

    public Trainer(String name, List<Pokemon> pokemon) {
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

    public List<Pokemon> getPokemon() {
        return pokemon;
    }

    public void setPokemon(List<Pokemon> pokemon) {
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