package com.javapoke;

import java.util.HashMap;

public class Pokemon {
    // fields
    String name;
    int level;
    int hitPoints;
    boolean isFainted = false;
    String attack;     // [ attackName : attack damage ], might have to change this to just a name
                                        // attack damage will be handled by a number randomizer

    public Pokemon(String name, int level, int hitPoints){
        this.name = name;
        this.level = level;
        this.hitPoints = hitPoints;
    }

    // constructors
    public Pokemon(String name, int level, int hitPoints, String attack) {
        this.name = name;
        this.level = level;
        this.hitPoints = hitPoints;
        this.attack = attack;
    }

    // accessors
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public boolean isFainted() {
        return isFainted;
    }

    public void setFainted(boolean fainted) {
        isFainted = fainted;
    }

    public String getAttack() {
        return attack;
    }

    public void setAttack(String attack) {
        this.attack = attack;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "name='" + name + '\'' +
                ", level=" + level +
                ", hitPoints=" + hitPoints +
                ", isFainted=" + isFainted +
                ", attack=" + attack +
                '}';
    }
}