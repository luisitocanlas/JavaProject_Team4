package com.javapoke;

/**
 * An enum representing a Potion item in the game.
 * Using a Potion item on a Pokemon will increase its HP by a certain amount.
 */
public enum Potion {

    /**
     * A Potion item that recovers a Pokemon's HP by 50.
     */
    POTION(50),

    /**
     * A Super Potion item that recovers a Pokemon's HP by 100.
     */
    SUPER_POTION(100);

    private final int value;

    /**
     * Constructs a Potion item with the given value.
     *
     * @param value the amount of HP to recover when using a Potion or Super_Potion item
     */
    Potion(int value) {
        this.value = value;
    }

    /**
     * Returns the value of this Potion item.
     *
     * @return the amount of HP to recover when using this Potion item
     */
    public int getValue() {
        return value;
    }
}