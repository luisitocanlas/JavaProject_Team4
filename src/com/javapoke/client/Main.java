package com.javapoke.client;

import com.javapoke.Pokemon;
import com.javapoke.app.JavaPokeApp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class Main {
    public static void main(String[] args) throws IOException {
        JavaPokeApp app = new JavaPokeApp();
//        Pokemon pokemon = new Pokemon("Blastoise",62,169,"Bite"
//                , Files.readString(Path.of("images/BlastoiseBest.txt")));
//        System.out.println(pokemon.getArt());
        // Uncomment Thursday night and make sure to run "run-splash.cmd" during presentation
//        app.welcome("images/Poke.png", "images/credits.png");
        app.start();
    }
}