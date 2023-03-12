package com.javapoke;

import com.javapoke.app.JavaPokeApp;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class PokeBattleTest {
    // fixture
    private Trainer ash;

    @Before
    public void setUp() {
        // create potion and add to list of potions
        Potion potion = new Potion("Potion", 10, 25);
        Potion superPotion = new Potion("Super Potion", 5, 50);
        List<Potion> myPotions = new ArrayList<>(List.of(potion, superPotion));

        // TODO: figure out how to do this when getting the data from the csv.file
        // create pokemon attacks
        HashMap<String, Double> charAttack = new HashMap<>();
        charAttack.put("Flamethrower", 1.15);

        HashMap<String, Double> squiAttack = new HashMap<>();
        squiAttack.put("Water gun", 10.5);

        // TODO: figure out how to do this when getting the data from the csv.file and user prompts
        // create pokemon and add the attacks
        Pokemon charmander = new Pokemon("Charmander", 62, 165, charAttack);
        Pokemon squirtle = new Pokemon("Squirtle", 60, 145, squiAttack);

        // TODO: figure out how to do this when getting the data from the csv.file and user prompts
        // create a pokemon storage that the trainer carries
        List<Pokemon> pokeBelt = new ArrayList<>(List.of(charmander, squirtle));

        ash = new Trainer("Ash", pokeBelt, myPotions);
    }

    @Test
    public void trainer_Chooses_Pokemon_fromCSV() {
        // might have to test this when running Main
    }

    @Test
    public void trainerAccess_Potion() {
        System.out.println(ash.getItems().get(1));
    }

    @Test
    public void trainerAccess_PokemonAttack() {
        System.out.println(ash.getPokemon().get(1).getAttack());
    }

    @Test
    public void trainerAccessTest() {
        System.out.println(ash.getClass().getSimpleName());
        System.out.println(ash.getName());
        System.out.println(ash.getPokemon());
        System.out.println(ash.getItems());
    }
}