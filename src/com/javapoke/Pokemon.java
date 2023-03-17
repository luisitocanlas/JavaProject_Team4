package com.javapoke;

import java.util.Random;

/**
 * This class is used to create Pokemon for our program.
 * @author Jorge Aponte and Lui Canlas
 * @version 1.0
 */
public class Pokemon {
    // fields
    private final String name;
    private final int level;
    private int hitPoints;
    private boolean isFainted = false;
    private final String attack;
    private String art;

    // constructors
    public Pokemon(String name, int level, int hitPoints, String attack)
    throws IllegalArgumentException{
        this.name = name;
        this.level = level;
        setHitPoints(hitPoints);
        this.attack = attack;
    }

    public Pokemon(String name, int level, int hitPoints, String attack, String art)
    throws IllegalArgumentException{
        this.name = name;
        this.level = level;
        setHitPoints(hitPoints);
        this.attack = attack;
        this.art = art;
    }

    /**
     * This method is used to generate an attack damage value that a given Pokémon intakes
     * This method will also update the other Pokemon's hp.
     * @param other The Pokémon in which is receiving the damage
     * @return The total amount of damage dealt to the Pokémon
     */
    // business methods
    public int attack(Pokemon other) {
        int damage = Attack.getDamage();
        other.takeDamage(damage);
        return damage;
    }

    private void takeDamage(int damage) {
        this.hitPoints -= damage;
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

    public void setHitPoints(int hitPoints) throws IllegalArgumentException {
        if (hitPoints < 0) {
            throw new IllegalArgumentException ("Hit points must be greater than 0. Input: " + hitPoints);
        } else {
            this.hitPoints = hitPoints;
        }
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

    //inner classes
    private static class Attack {

        private static int getDamage() {
            Random random = new Random();
            // damage will be between 30~60, attack modifier will be for future code
            return random.nextInt(30) + 30;
        }
    }
}