package com.javapoke;

import java.util.List;

public class Trainer {
    //fields
    String name;
    List<Pokemon> pokemon;
    List<Potion> items;

    //constructors
    public Trainer(String name) {
        this.name = name;
    }

    public Trainer(String name, List<Pokemon> pokemon, List<Potion> items) {
        this.name = name;
        this.pokemon = pokemon;
        this.items = items;
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

    public List<Potion> getItems() {
        return items;
    }

    public void setItems(List<Potion> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "name='" + name + '\'' +
                ", pokemon=" + pokemon +
                ", items=" + items +
                '}';
    }
}