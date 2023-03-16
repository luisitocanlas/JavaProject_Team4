package com.javapoke.client;

import com.javapoke.EliteTrainer;
import com.javapoke.Pokemon;
import com.javapoke.Trainer;
import com.javapoke.app.JavaPokeApp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class Main {
    public static void main(String[] args) throws IOException {
        JavaPokeApp app = new JavaPokeApp();
//        Pokemon pokemon = new Pokemon("Blastoise",62,169,"Bite"
//                , Files.readString(Path.of("images/pokemon/Alakazam.txt")));
//        EliteTrainer lorelei = new EliteTrainer().loadLorelei();
//        System.out.println(lorelei);
//        System.out.println(pokemon.getArt());

//        EliteTrainer lance = new EliteTrainer().loadLance();

//        elite1 = (EliteTrainer) elite1.loadLance();
//        System.out.println(lance);
        // Uncomment Thursday night and make sure to run "run-splash.cmd" during presentation
//        app.welcome("images/Poke.png", "images/credits.png");
        app.start();
    }
}