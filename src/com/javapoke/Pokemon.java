package com.javapoke;

public class Pokemon {
    // fields
    private final String name;
    private final int level;
    private int hitPoints;
    private boolean isFainted = false;
    private String attack;     // [ attackName : attack damage ], might have to change this to just a name
    private String art;                       // attack damage will be handled by a number randomizer

    // constructors
    public Pokemon(String name, int level, int hitPoints){
        this.name = name;
        this.level = level;
        this.hitPoints = hitPoints;
    }

    public Pokemon(String name, int level, int hitPoints, String attack) {
        this.name = name;
        this.level = level;
        this.hitPoints = hitPoints;
        this.attack = attack;
    }

    public Pokemon(String name, int level, int hitPoints, String attack, String art) {
        this.name = name;
        this.level = level;
        this.hitPoints = hitPoints;
        this.attack = attack;
        this.art = art;
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

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "name='" + name + '\'' +
                ", level=" + level +
                ", hitPoints=" + hitPoints +
                ", isFainted=" + isFainted +
                ", attack=" + attack +
                ", art=" + art + '}';
    }
}