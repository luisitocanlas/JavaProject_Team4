package com.javapoke;

import java.util.HashMap;

public class Pokemon {
    // fields
    String name;
    int level;
    int hitPoints;
    boolean isFainted = false;
    HashMap<String, Double> attack;

    // constructors
    public Pokemon(String name, int level, int hitPoints, HashMap<String, Double> attack) {
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

    public void setLevel(int level) {
        this.level = level;
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

    public HashMap<String, Double> getAttack() {
        return attack;
    }

    public void setAttack(HashMap<String, Double> attack) {  // put attack(s) and its value for the pokemon to use
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