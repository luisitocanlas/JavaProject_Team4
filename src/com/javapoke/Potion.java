package com.javapoke;

public enum Potion {
    POTION(50),
    SUPER_POTION(100);

    private final int value;

    Potion(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}