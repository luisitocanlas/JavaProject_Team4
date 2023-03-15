package com.javapoke.app;

import com.apps.util.Console;
import com.apps.util.Prompter;
import com.javapoke.Pokemon;
import com.javapoke.Trainer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static com.apps.util.Console.*;
import static com.javapoke.Potion.*;


public class PokeBattle {
    // fields
    private final Prompter prompter;
    private static boolean inBattle = false;
    private static int potion = 10;
    private static int superPotion = 5;
    private Pokemon activePokemon;
    private Trainer trainer;

    Map<Integer, Pokemon> pokeStorage;      // this will be connected to the player's selection

    // ctor
    public PokeBattle(Prompter prompter) {
        this.prompter = prompter;
    }

//    public PokeBattle getInstance() {
//        return new PokeBattle(prompter);
//    }

    /*
     * Code within the dashes are used for method functionality, may keep some of them----------------------------------
     */
    // create pokemon and add the attacks
    Pokemon charmander = new Pokemon("Charmander", 62, 1, "Ember");
    Pokemon squirtle = new Pokemon("Squirtle", 60, 1, "Water ball");
    Pokemon bulbasaur = new Pokemon("Bulbasaur", 60, 1, "Vine Whip");
    Pokemon lapras = new Pokemon("Lapras", 60, 1, "Blizzard");
    Pokemon machamp = new Pokemon("Machamp", 60, 1, "Body Slam");
    Pokemon gengar = new Pokemon("Gengar", 60, 1, "Night Shade");
    Pokemon dragonite = new Pokemon("Dragonite", 60, 1, "Hyper Beam");
    Pokemon mewtwo = new Pokemon("Mewtwo", 60, 1, "Psychic");

    // create player and opponents
    Trainer elite1 = new Trainer("Lorelei", Map.of(1,lapras, 2,charmander, 3,squirtle, 4,bulbasaur));
    Trainer elite2 = new Trainer("Bruno", Map.of(1,machamp, 2,charmander, 3,squirtle, 4,bulbasaur));
    Trainer elite3 = new Trainer("Agatha", Map.of(1,gengar, 2,charmander, 3,squirtle, 4,bulbasaur));
    Trainer elite4 = new Trainer("Lance", Map.of(1,dragonite, 2,charmander, 3,squirtle, 4,bulbasaur));
    Trainer surprise = new Trainer("THE Joshua BLOCH", Map.of(1,mewtwo, 2,charmander, 3,squirtle, 4,bulbasaur));
    //------------------------------------------------------------------------------------------------------------------

    /*
     * Containers-------------------------------------------------------------------------------------------------------
     */
    // TODO: return activePokemon to container outside of the methods
    // TODO: separate the surprise trainer from the opponents and have a method to call when all the elite 4 are defeated
    // list of opponents to go through, data will be from csv
    Map<Integer, Trainer> opponents = Map.of(1,elite1, 2,elite2, 3,elite3, 4,elite4);

    // container for active opponent, and their active pokemon, will initialize for the first opponent
    Trainer activeOpponent = elite1;
    Pokemon opponentPokemon = activeOpponent.getPokemon().get(1);



    /*
     * Main methods-----------------------------------------------------------------------------------------------------
     */
    // this method is for getting the data from the prompts and storing them in the containers
    public Trainer startPokeBattle(Trainer player) {
        trainer = player;
        // container for the active pokemon for the current battle, should be getting from user
        activePokemon = trainer.getPokemon().get(1);
        // go to battle!
        battle();
        return trainer;
    }

    // create a method for the battle
    public void battle() {
        clear();
        inBattle = true;

        // show activePokemon details here
        System.out.println(activePokemon);
        // show opponentPokemon details here
        System.out.println(opponentPokemon);
        blankLines(1);

        System.out.println("[1] Fight      [3] Switch Pokemon");
        System.out.println("[2] Use Item   [4] Run Away");
        blankLines(1);
        String battlePrompt = prompter.prompt("What will you do? ", "1|2|3|4", "\nThis is not a valid option!\n");
        switch (Integer.parseInt(battlePrompt)) {
            case 1:
                fight();
                break;
            case 2:
                useItem();
                break;
            case 3:
                switchPokemon();
                break;
            case 4:
                runAway();
                break;
        }
    }

    public void fight() {
        clear();

        // player attacks
        int playerAttack = activePokemon.attack(opponentPokemon);
        System.out.printf("%s used %s\n", activePokemon.getName(), activePokemon.getAttack());
        System.out.printf("Deals %s points of damage to %s\n", playerAttack, opponentPokemon.getName());
        blankLines(1);
        pause(2_000);

        // TODO: there should be a check here for HP if you felled the opponent because fainted pokemon don't attack
        
        // opponent attacks,TODO: maybe put this in an if else when opponent doesn't faint from our attack
        opponentTurn();

        // check for hit points for both sides
        if (activePokemon.getHitPoints() <= 0) {        // if your pokemon fainted, automatically call switch pokemon
            System.out.printf("%s fainted from battle.\n", activePokemon.getName());
            blankLines(1);
            activePokemon.setFainted(true);
            inBattle = false;
            pause(2_000);
            switchPokemon();
        }
        else if (opponentPokemon.getHitPoints() <= 0) { // if your opponent's pokemon fainted
            System.out.printf("%s's %s fainted from battle.\n", activeOpponent.getName(), opponentPokemon.getName());
            blankLines(1);
            opponentPokemon.setFainted(true);
            inBattle = false;
            pause(2_000);
            nextPokemon();
        }
        //end of turn
        battle();
    }

    public void useItem() {
        clear();
        // ask the player which potion to use, TODO: maybe switch this to case
        System.out.printf("[1] Potion: %8s\n", potion);
        System.out.printf("[2] Super potion: %2s\n", superPotion);
        System.out.println("[3] Back\n");
        String prompt = prompter.prompt("Pick your poison: ", "1|2|3",
                "\nThis is not a valid option!\n");
        if (potion >= 1 && Integer.parseInt(prompt) == 1) {
            potion -= 1;
            activePokemon.setHitPoints(activePokemon.getHitPoints() + POTION.getValue());
            System.out.printf("You used Potion! Potion remaining: %s\n", potion);
            System.out.printf("%s healed by %s points\n", activePokemon.getName(), POTION.getValue());
            blankLines(1);
        }
        else if (superPotion >= 1 && Integer.parseInt(prompt) == 2) {
            superPotion -= 1;
            activePokemon.setHitPoints(activePokemon.getHitPoints() + SUPER_POTION.getValue());
            System.out.printf("You used Super Potion! Super Potion remaining: %s\n", superPotion);
            System.out.printf("%s healed by %s points\n", activePokemon.getName(), SUPER_POTION.getValue());
            blankLines(1);
        }
        else if (Integer.parseInt(prompt) == 3) {
            battle();
        }
        else if (potion == 0 && superPotion == 0) {
            System.out.println("You have ran out of potions!\n");
            blankLines(1);
        }
        pause(2_500);
        // computer attacks your pokemon
//        opponentTurn();
        // go back to main battle options
        battle();
    }

    public void switchPokemon() {   // TODO: add an option to go back
        clear();
        System.out.println("Choose a Pokemon.");
        blankLines(1);
        // if in a battle [inBattle = true]
        if (!inBattle) {
            // will output the list of pokemon available, their LVL and HP, and user will select from the list
            pokeSwitch();
            // computer attacks your pokemon
//            opponentTurn();
        }
        // else if you are in between fights [inBattle=false]
        else if (inBattle) {
            // will output the list of pokemon available, their LVL and HP, and user will select from the list
            pokeSwitch();
            // switch [inBattle=true]
            inBattle = true;
        }
        // go back to main battle options
        battle();
    }

    public void runAway() {
        clear();
        // TODO: go straight to game over, maybe say something cheeky
        System.out.println("GAME OVER!!!");
        pause(2_000);
    }

    /*
     * Minor methods----------------------------------------------------------------------------------------------------
     */
    // TODO: it's not ending when all fainted and just looping, change to while loop
    public void nextPokemon() {     // just for the current opponent's pokemon
        clear();
        boolean allFainted = true;
        for (Pokemon pokemon : activeOpponent.getPokemon().values()) {
            if (!pokemon.isFainted()) {
                allFainted = false;
                opponentPokemon = pokemon;
                System.out.printf("%s selected %s.\n", activeOpponent.getName(), opponentPokemon.getName());
                pause(2_000);
                blankLines(1);
                break;
            }
        }
        if (allFainted) {
            System.out.printf("You defeated %s!", activeOpponent.getName());
            pause(2_000);
            blankLines(1);
            runAway();
//            nextOpponent();
        }
    }

    // TODO: it's not switching to the next opponent, might change to a while loop
    public void nextOpponent() {    // just for switching the opponents
        clear();
        boolean noPokemon = true;
        for (Map.Entry<Integer, Trainer> opponent : opponents.entrySet()){
            boolean allFainted = true;
            for (Pokemon pokemon : opponent.getValue().getPokemon().values()) {
                if (!pokemon.isFainted()) {
                    allFainted = false;
                    noPokemon = false;
                    activeOpponent = opponent.getValue();
                    opponentPokemon = pokemon;
                    break;
                }
            }
            if (allFainted) {
                noPokemon = true;
                System.out.println("You defeated the Elite 4 and the surprise champion!\n");
                blankLines(1);
//                runAway();
            }
        }
        if (noPokemon) {
            System.out.println("You defeated the Elite 4 and the surprise champion!\n");
            blankLines(1);
            runAway();
        }
    }

    //should go to game over when all of your pokemon are dead
    private void pokeSwitch() {
        clear();
        System.out.println(" Option     Pokemon       Level    HP    isFainted");
        System.out.println("  ======   ============    =====   ====   =========");
        for (Map.Entry<Integer, Pokemon> pokemon : trainer.getPokemon().entrySet()) {
            System.out.printf("%5s:      %-10s      %s      %-5s   %s\n"
                    , pokemon.getKey(), pokemon.getValue().getName()
                    , pokemon.getValue().getLevel(), pokemon.getValue().getHitPoints(), pokemon.getValue().isFainted());
        }

        boolean allFainted = true;
        for (Pokemon pokemon : trainer.getPokemon().values()) {
            if (!pokemon.isFainted()) {
                allFainted = false;
                break;
            }
        }

        if (allFainted) {
            System.out.println("All of your Pokemon fainted from battle");
            pause(2_000);
            blankLines(1);
            runAway();
        }

        for (int i = 0; i < trainer.getPokemon().size(); i++) {
            String pokemonPrompt = prompter.prompt("Select pokemon #: ", "1|2|3|4", "\nThis is not a valid option!\n");
            Pokemon selectedPokemon = trainer.getPokemon().get(Integer.parseInt(pokemonPrompt));
            if (!selectedPokemon.isFainted()) {
                activePokemon = selectedPokemon;
                System.out.printf("You selected %s.\n", activePokemon.getName());
                pause(2_000);
                blankLines(1);
                break;
            }
            else if (selectedPokemon.isFainted()) {
                System.out.println("Cannot select pokemon\n");
                i--;
                pause(2_000);
                blankLines(1);
            }
        }
    }

    private void opponentTurn() {   // TODO: might need to adjust damage for difficulty
        int opponentAttack = opponentPokemon.attack(activePokemon);
        System.out.printf("%s used %s\n", opponentPokemon.getName(), opponentPokemon.getAttack());
        System.out.printf("Deals %s points of damage to %s\n", opponentAttack, activePokemon.getName());
        Console.blankLines(1);
        pause(2_000);
    }
}