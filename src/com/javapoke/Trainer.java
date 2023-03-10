package com.javapoke;

import java.util.HashMap;

public class Trainer {
    //fields
    String name;
    HashMap<String, Integer> items;

    //constructors
    public Trainer(String name) {
        this.name = name;
    }

    // methods
    void useItems() {

    }

    void switchPokemon() {

    }

    //accessors
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}