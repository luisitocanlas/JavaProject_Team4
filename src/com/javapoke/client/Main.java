package com.javapoke.client;

import com.javapoke.app.JavaPokeApp;

class Main {
    public static void main(String[] args) {
        JavaPokeApp app = new JavaPokeApp();
        // Uncomment Thursday night
//        app.welcome("images/Poke.png", "images/credits.png");
        app.start();
    }
}