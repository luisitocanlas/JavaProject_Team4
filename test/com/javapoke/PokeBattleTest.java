package com.javapoke;

import com.apps.util.Console;
import com.apps.util.Prompter;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Stream;

import static com.javapoke.Potion.*;

public class PokeBattleTest {
    // fixture
    private Prompter prompter;
    Trainer trainer;
    Trainer elite1;
    Trainer elite2;
    Trainer elite3;
    Trainer elite4;
    Trainer surprise;
    Pokemon activePokemon;
    Pokemon charmander;
    Pokemon squirtle;
    Pokemon bulbasaur;
    Pokemon lapras;
    Pokemon machamp;
    Pokemon gengar;
    Pokemon dragonite;
    Pokemon mewtwo;
    Map<Integer, Pokemon> pokeBelt;
    Map<Integer, Pokemon> pokeBelt2;
    List<Trainer> opponents;
    Trainer activeOpponent;

    // potion counter
    int potion = 10;
    int superPotion = 5;

    boolean inBattle = false;   // will be a check when switching pokemon

    @Before
    public void setUp() {
         prompter = new Prompter(new Scanner(System.in));

        // TODO: figure out how to do this when getting the data from the csv.file and user prompts
        // create pokemon and add the attacks
        charmander = new Pokemon("Charmander", 62, 165, "Ember");
        squirtle = new Pokemon("Squirtle", 60, 145, "Water ball");
        bulbasaur = new Pokemon("Bulbasaur", 61, 155, "Vine Whip");
        lapras = new Pokemon("Lapras", 60, 130, "Blizzard");
        machamp = new Pokemon("Machamp", 60, 90, "Body Slam");
        gengar = new Pokemon("Gengar", 60, 60, "Night Shade");
        dragonite = new Pokemon("Dragonite", 60, 91, "Hyper Beam");
        mewtwo = new Pokemon("Mewtwo", 60, 106, "Psychic");

        // TODO: figure out how to do this when getting the data from the csv.file and user prompts
        // create a pokemon storage that the trainer carries
        pokeBelt = Map.of(1,charmander, 2,squirtle, 3,bulbasaur);
        pokeBelt2 = Map.of(1,squirtle, 2,charmander, 3,bulbasaur);

        // TODO: implement the option to choose from a list of names or type in their own name
        trainer = new Trainer("Ash", pokeBelt);              // will be replaced by the chooserTrainer()
        elite1 = new Trainer("Lorelei", Map.of(1, lapras));
        elite2 = new Trainer("Bruno", Map.of(1,machamp));
        elite3 = new Trainer("Agatha", Map.of(1,gengar));
        elite4 = new Trainer("Lance", Map.of(1,dragonite));
        surprise = new Trainer("THE Joshua BLOCH", Map.of(1,mewtwo));

        // TODO: implement something that tells you which is the current active pokemon
        // container for the active pokemon for the current battle
        activePokemon = trainer.getPokemon().get(1);

        // list of opponents to go through
        opponents = new ArrayList<>(List.of(elite1, elite2, elite3, elite4, surprise));

        // container for active opponent, maybe change this to the opponent's pokemon instead of the trainer
        activeOpponent = elite1;
    }

    @Test
    public void fight_attackAndReceiveDamage() {
        // show activePokemon
        System.out.println(activePokemon);
        // show activeOpponent's pokemon
        System.out.println(activeOpponent.getPokemon().get(1));
        System.out.println();

        // player attacks
        int damage = attackDamage();
        System.out.printf("%s uses %s\n", activePokemon.getName(), activePokemon.getAttack());
        System.out.printf("Deals %s points of damage to %s\n", damage,
                activeOpponent.getPokemon().get(1).getName());
        activeOpponent.getPokemon().get(1).setHitPoints(activeOpponent.getPokemon().get(1).getHitPoints()
                - damage);
        System.out.println(activeOpponent.getPokemon().get(1));
        System.out.println();

        // opponent attacks
        int damage2 = attackDamage();
        System.out.printf("%s uses %s\n", activeOpponent.getPokemon().get(1).getName(),
                activeOpponent.getPokemon().get(1).getAttack());
        System.out.printf("Deals %s points of damage to %s\n", damage2, activePokemon.getName());
        activePokemon.setHitPoints(activePokemon.getHitPoints() - damage2);
        System.out.println(activePokemon);
        System.out.println();

        //end of turn
    }

    private int attackDamage() {
        int pain = 0;
        Random random = new Random();
        pain = random.nextInt(30) + 1;      // damage will be between 1~30, attack modifier will be for future code
        return pain;
    }

    @Test
    public void switchingPokemon() {
        inBattle = true;
        bulbasaur.setFainted(true);

        if (!inBattle) {
            // will output the list of pokemon available, their LVL and HP, and user will select from the list
            pokeSwitch();
            // computer attacks your pokemon
        }
        // else if you are in between fights [inBattle=false]
        else if (inBattle) {
            // will output the list of pokemon available, their LVL and HP, and user will select from the list
            pokeSwitch();
            // go to the next battle
            // switch [inBattle=true]
            inBattle = true;
        }
//        System.out.println(inBattle);
    }

    private void pokeSwitch() {
        System.out.println(" Option \tPokemon \tLevel \tHP");
        System.out.println(" ====== \t======= \t===== \t===");
        for (Map.Entry<Integer, Pokemon> pokemon : pokeBelt.entrySet()) {
            System.out.printf("   %s: \t\t%s \t %s \t%s\n"
                    , pokemon.getKey(), pokemon.getValue().getName()
                    , pokemon.getValue().getLevel(), pokemon.getValue().getHitPoints());
        }
        String pokemonPrompt = "3";
//        String pokemonPrompt = prompter.prompt("Select pokemon #", "1|2|3|4", "\nThis is not a valid option!\n");
        if (trainer.getPokemon().get(Integer.parseInt(pokemonPrompt)).isFainted() ||
                trainer.getPokemon().get(Integer.parseInt(pokemonPrompt)).getHitPoints() <= 0) {
            System.out.println("Cannot select pokemon");
        }
        else {
            activePokemon = trainer.getPokemon().get(Integer.parseInt(pokemonPrompt));
            System.out.printf("%s is selected.", activePokemon.getName());
        }
    }

    @Test
    public void pokeBattle_damageTest() {
        int pain = 0;
        Random random = new Random();
        pain = random.nextInt(30) + 1;
        System.out.println(pain);
    }

    @Test
    public void useItem_promptTest() {
        String prompt = "2";
//        potion = 0;
//        superPotion = 0;

        if (potion >= 1 && Integer.parseInt(prompt) == 1) {
            potion -= 1;
            activePokemon.setHitPoints(activePokemon.getHitPoints() + POTION.getValue());
            System.out.printf("You used Potion! Potion remaining: %s\n", potion);
            System.out.printf("%s healed by %s points", activePokemon.getName(), POTION.getValue());
        }
        else if (superPotion >= 1 && Integer.parseInt(prompt) == 2) {
            superPotion -= 1;
            activePokemon.setHitPoints(activePokemon.getHitPoints() + SUPER_POTION.getValue());
            System.out.printf("You used Super Potion! Super Potion remaining: %s\n", superPotion);
            System.out.printf("%s healed by %s points", activePokemon.getName(), SUPER_POTION.getValue());
        }
        else if (potion == 0 && superPotion == 0) {
            System.out.println("You have ran out of potions!");
        }
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