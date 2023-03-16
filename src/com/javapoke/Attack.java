package com.javapoke;

import java.util.Random;

class Attack {

    public static int getDamage() {
        Random random = new Random();
        // damage will be between 30~60, attack modifier will be for future code
        return random.nextInt(30) + 30;
    }
}