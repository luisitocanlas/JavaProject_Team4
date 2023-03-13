package com.javapoke;

enum Potion {
    POTION(25),
    SUPER_POTION(50);

    private final int value;

    private Potion(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}