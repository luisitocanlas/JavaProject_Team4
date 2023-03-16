package com.javapoke;

import java.util.Random;

class Attack {

    public static int getDamage() {
        int pain = 0;
        Random random = new Random();
        pain = random.nextInt(30) + 30;      // damage will be between 30~60, attack modifier will be for future code
        return pain;
    }
}