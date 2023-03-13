package com.javapoke;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static com.javapoke.Potion.*;

public class PokeBattleTest {
    // fixture
    Trainer trainer;
    Pokemon activePokemon;
    List<Trainer> opponents;
    Trainer activeOpponent;
    List<Pokemon> pokeBelt;
    List<Pokemon> pokeBelt2;

    // potion counter
    int potion = 10;
    int superPotion = 5;

    boolean inBattle = false;   // will be a check when switching pokemon

    @Before
    public void setUp() {
        // TODO: figure out how to do this when getting the data from the csv.file and user prompts
        // create pokemon and add the attacks
        Pokemon charmander = new Pokemon("Charmander", 62, 165, "Ember");
        Pokemon squirtle = new Pokemon("Squirtle", 60, 145, "Water ball");
        Pokemon lapras = new Pokemon("Lapras", 60, 130, "Blizzard");
        Pokemon machamp = new Pokemon("Machamp", 60, 90, "Body Slam");
        Pokemon gengar = new Pokemon("Gengar", 60, 60, "Night Shade");
        Pokemon dragonite = new Pokemon("Dragonite", 60, 91, "Hyper Beam");
        Pokemon mewtwo = new Pokemon("Mewtwo", 60, 106, "Psychic");

        // TODO: figure out how to do this when getting the data from the csv.file and user prompts
        // create a pokemon storage that the trainer carries
        pokeBelt = new ArrayList<>(List.of(charmander, squirtle));
        pokeBelt2 = new ArrayList<>(List.of(squirtle, charmander));

        // TODO: implement the option to choose from a list of names or type in their own name
        Trainer trainer = new Trainer("Ash", pokeBelt);              // will be replaced by the chooserTrainer()
        Trainer elite1 = new Trainer("Lorelei", new ArrayList<>(List.of(lapras)));
        Trainer elite2 = new Trainer("Bruno", new ArrayList<>(List.of(machamp)));
        Trainer elite3 = new Trainer("Agatha", new ArrayList<>(List.of(gengar)));
        Trainer elite4 = new Trainer("Lance", new ArrayList<>(List.of(dragonite)));
        Trainer surprise = new Trainer("THE Joshua BLOCH", new ArrayList<>(List.of(mewtwo)));

        // TODO: implement something that tells you which is the current active pokemon
        // container for the active pokemon for the current battle
        activePokemon = trainer.getPokemon().get(0);

        // list of opponents to go through
        opponents = new ArrayList<>(List.of(elite1, elite2, elite3, elite4, surprise));

        // container for active opponent
        activeOpponent = elite1;
    }

    @Test
    public void switchingPokemon() {
        inBattle = false;

        if (!inBattle) {
            // will output the list of pokemon available, their LVL and HP, and user will select from the list
            for (Pokemon pokemon : pokeBelt) {
                System.out.println(pokemon);
            }
            // cannot select pokemon with isFainted=true
            // set activePokeman
            // computer attacks your pokemon
        }

        // else if you are in between fights [inBattle=false]
        else {
            // will output the list of pokemon available, their LVL and HP, and user will select from the list
            for (Pokemon pokemon : pokeBelt2) {
                System.out.println(pokemon);
            }
            // cannot select pokemon with isFainted=true
            // set activePokeman
            // go to the next battle
            // switch [inBattle=true]
            inBattle = true;
        }
        System.out.println(inBattle);
    }

    @Test
    public void pokeBattle_damageTest() {
        int pain = 0;
        Random random = new Random();
        pain = random.nextInt(30) + 1;
        System.out.println(pain);
    }

    @Test
    public void trainerAccess_Potion() {
        Stream.of(Potion.values()).forEach(System.out::println); // show list of potions
        System.out.println();

        System.out.println(potion);                              // check values
        System.out.println(activePokemon.getHitPoints());
        System.out.println();

        activePokemon.setHitPoints(96);                          // change hp value
        System.out.println(activePokemon.getHitPoints());
        System.out.println();

        potion -= 1;                                             // use potion
        activePokemon.setHitPoints(activePokemon.getHitPoints() + POTION.getValue());
        System.out.println(potion);
        System.out.println(activePokemon.getHitPoints());
        System.out.println();
    }

    @Test
    public void trainerAccess_PokemonAttack() {
        System.out.println(activePokemon);
        System.out.println(trainer.getPokemon().get(1).getAttack());
    }

    @Test
    public void trainerAccessTest() {
        System.out.println(trainer.getClass().getSimpleName());
        System.out.println(trainer.getName());
        System.out.println(trainer.getPokemon());
    }
}