package com.javapoke;

public class Pokemon {
    // fields
    private final String name;
    private final int level;
    private int hitPoints;
    private boolean isFainted = false;
    private final String attack;
    private String art;

    // constructors
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
    // TODO: Ask Lui how is he updating the Pokemon's health
    public int attack(Pokemon other) {
        int damage = Attack.getDamage();
        other.takeDamage(damage);
        return damage;
    }
    // TODO: This method could help with the other TODO suggestion
    private int takeDamage(int damage) {
        return this.hitPoints -= damage;
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

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": name=" + name + ", level=" + level + ", hitPoints=" +
                hitPoints + ", isFainted=" + isFainted + ", attack=" + attack + ", art=\n" + art;
    }
}