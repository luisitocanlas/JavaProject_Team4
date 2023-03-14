package com.javapoke;

import java.util.Random;

class Attack {

    public static int getDamage() {
        int pain = 0;
        Random random = new Random();
        pain = random.nextInt(30) + 15;      // damage will be between 15~45, attack modifier will be for future code
        return pain;
    }
}