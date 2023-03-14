package com.javapoke;

import java.util.HashMap;

public class Pokemon {
    // fields
    private final String name;
    private final int level;
    private int hitPoints;
    private boolean isFainted = false;
    private String attack;     // [ attackName : attack damage ], might have to change this to just a name
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

    /*
     * int damage = Attack.getDamage()
     * other.setHitPoints -= damage;
     */
    public int attack(Pokemon other) {
        int damage = Attack.getDamage();
        other.takeDamage(damage);
        return damage;
    }

    // accessors
    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    private int takeDamage(int damage) {
        return this.hitPoints -= damage;
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