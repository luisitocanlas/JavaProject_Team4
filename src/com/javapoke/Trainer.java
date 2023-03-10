package com.javapoke;

import java.util.HashMap;
import java.util.List;

public class Trainer {
    //fields
    String name;
    HashMap<String, List<Pokemon>> pokemon;
    HashMap<String, Integer> items;

    //constructors
    public Trainer(String name) {
        this.name = name;
    }

    //accessors
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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