package com.javapoke;

import java.util.HashMap;

class Pokemon {
    // fields
    String name;
    int level;
    int hitPoints;
    boolean isFainted = false;
    HashMap<String, Double> fight;


    // constructor
    public Pokemon(String name, int level, int hitPoints) {
        this.name = name;
        this.level = level;
        this.hitPoints = hitPoints;
    }

    // accessors
    public String getName() {
        return name;
    }


}