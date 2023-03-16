package com.javapoke.app;

import com.apps.util.Console;
import com.apps.util.Prompter;
import com.javapoke.EliteTrainer;
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
    private static boolean inBattle = false;
    private static int potion = 10;
    private static int superPotion = 5;
    private static int maxHP;
    private static int maxHP1;
    private static int maxHP2;
    private static int maxHP3;
    private static int maxHP4;

    private final Prompter prompter;
    private Pokemon activePokemon;
    private Trainer trainer;
    private final Pokemon mewtwo = new Pokemon("Mewtwo", 60, 150, "Psychic");
    private final EliteTrainer lorelei = new EliteTrainer().loadLorelei();
    private final EliteTrainer bruno = new EliteTrainer().loadBruno();
    private final EliteTrainer agatha = new EliteTrainer().loadAgatha();
    private final EliteTrainer lance = new EliteTrainer().loadLance();
    private final Trainer surprise = new Trainer("THE Joshua BLOCH", Map.of(1, mewtwo));
    private final Map<Integer, Trainer> opponents = new TreeMap<>(Map.of(1, lorelei, 2, bruno, 3, agatha, 4, lance));
    private boolean gameOver = false;

    // container for active opponent, and their active pokemon, will initialize for the first opponent
    private Trainer activeOpponent = lorelei;
    private Pokemon opponentPokemon = activeOpponent.getPokemon().get(1);

    // ctor
    PokeBattle(Prompter prompter) {
        this.prompter = prompter;
    }

    /*
     * Main methods-----------------------------------------------------------------------------------------------------
     */
    // this method is for getting the data from the prompts and storing them in the containers
    void startPokeBattle(Trainer player) {
        trainer = player;
        // container for the active pokemon for the current battle, should be getting from user
        activePokemon = trainer.getPokemon().get(1);
        setMaxHP(); // sets maxHP used for healing your pokemon

        // go to battle!
        battle();
    }

    private void battleOver() {
        gameOver = true;
        System.out.println("I can't believe it...");
        pause(2_500);
        System.out.printf("You really beat %s!\n", surprise.getName());
        pause(2_500);
        System.out.println("But there are more challenges ahead, brace yourself\n");
        pause(2_500);
    }

    // create a method for the battle
    private void battle() {
        clear();
        while (!gameOver) {
            inBattle = true;

            // show opponentPokemon details here
            System.out.println(activeOpponent.getName());
            System.out.printf("%-14s   HP: %s", opponentPokemon.getName(), opponentPokemon.getHitPoints());
            // TODO showcase Pokemon Art
            blankLines(2);
            // show activePokemon details here
            System.out.println(trainer.getName());
            System.out.printf("%-14s   HP: %s ", activePokemon.getName(), activePokemon.getHitPoints());

            blankLines(1);

            try {
                Files.readAllLines(Path.of("images/battle_Prompt.txt"))
                        .forEach(System.out::println);
            } catch (IOException e) {
                e.printStackTrace();
            }

            blankLines(1);
            String battlePrompt = prompter.prompt("\t What will you do? ", "1|2|3|4",
                    "\n\t This is not a valid option!\n");
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
    }

    private void fight() {
        clear();
        // player attacks
        int playerAttack = activePokemon.attack(opponentPokemon);
        System.out.printf("%s used %s\n", activePokemon.getName(), activePokemon.getAttack());
        System.out.printf("Deals %s points of damage to %s\n", playerAttack, opponentPokemon.getName());
        blankLines(1);
        pause(2_000);

        // checks if your opponents hit points
        if (opponentPokemon.getHitPoints() <= 0) {
            System.out.printf("%s's %s fainted from battle.\n", activeOpponent.getName(), opponentPokemon.getName());
            blankLines(1);
            opponentPokemon.setFainted(true);
            opponentPokemon.setHitPoints(0);
            inBattle = false;
            pause(2_000);
            nextPokemon();
        } else {
            opponentTurn();
        }
    }

    private void useItem() {
        clear();
        // ask the player which potion to use, TODO: maybe switch this to case
        System.out.printf("[1] Potion: %8s\n", potion);
        System.out.printf("[2] Super potion: %2s\n", superPotion);
        System.out.println("[3] Back\n");
        String prompt = prompter.prompt("Pick your poison: ", "1|2|3",
                "\nThis is not a valid option!\n");
        if (potion >= 1 && Integer.parseInt(prompt) == 1) {
            potion -= 1;

//            maxHP = pullMaxHP();
            if (activePokemon.getHitPoints() + POTION.getValue() > maxHP) {
                activePokemon.setHitPoints(maxHP);
            }
            else {
                activePokemon.setHitPoints(activePokemon.getHitPoints() + POTION.getValue());
            }


            System.out.printf("You used Potion! Potion remaining: %s\n", potion);
            System.out.printf("%s healed by %s points\n", activePokemon.getName(), POTION.getValue());
            blankLines(1);
        } else if (superPotion >= 1 && Integer.parseInt(prompt) == 2) {
            superPotion -= 1;

//            max = pullMaxHP();
            if (activePokemon.getHitPoints() + SUPER_POTION.getValue() > maxHP) {
                activePokemon.setHitPoints(maxHP);
            }
            else{
                activePokemon.setHitPoints(activePokemon.getHitPoints() + SUPER_POTION.getValue());
            }


            System.out.printf("You used Super Potion! Super Potion remaining: %s\n", superPotion);
            System.out.printf("%s healed by %s points\n", activePokemon.getName(), SUPER_POTION.getValue());
            blankLines(1);
        } else if (Integer.parseInt(prompt) == 3) {
            battle();
        } else if (potion == 0 && superPotion == 0) {
            System.out.println("You have ran out of potions!\n");
            blankLines(1);
        }
        pause(2_500);
        // go back to main battle options
        battle();
    }

    private void switchPokemon() {   // TODO: add an option to go back
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
        else {
            // will output the list of pokemon available, their LVL and HP, and user will select from the list
            pokeSwitch();
            // switch [inBattle=true]
            inBattle = true;
        }
    }

    private void runAway() {
        clear();
        try {
            Files.readAllLines(Path.of("images/runAwayBanner.txt"))
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
        pause(2_500);
        gameOver = true;
    }

    /*
     * Minor methods----------------------------------------------------------------------------------------------------
     */
    private void nextPokemon() {     // just for the current opponent's pokemon
        clear();
        boolean allFainted = true;
        for (Pokemon pokemon : activeOpponent.getPokemon().values()) {
            if (!pokemon.isFainted()) {
                allFainted = false;
                opponentPokemon = pokemon;
                System.out.printf("%s selected %s.\n", activeOpponent.getName(), opponentPokemon.getName());
                pause(2_000);
                blankLines(1);
                battle();
                break;
            }
        }
        if (allFainted) {
            if (surprise.getName().equals(activeOpponent.getName())) {  // true ending
                System.out.printf("Congratulations on defeating %s!", activeOpponent.getName());
                pause(2_000);
                blankLines(1);
                battleOver();
            } else {                                                      // goes to continue or give up
                System.out.printf("Congratulations on defeating %s!", activeOpponent.getName());
                pause(2_000);
                blankLines(1);
                fightOn();
            }
        }
    }

    private void fightOn() { // ask the player if they want to continue fighting or give up.
        clear();
        try {
            Files.readAllLines(Path.of("images/continue_Prompt.txt"))
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String continuePrompt = prompter.prompt("\t What will you do? ", "1|2",
                "\n\t This is not a valid option!\n");
        switch (Integer.parseInt(continuePrompt)) {
            case 1:
                nextOpponent();
                break;
            case 2:
                runAway();
                break;
        }
    }

    private void nextOpponent() {    // just for switching the opponents
        clear();
        boolean hasNoPokemon = true;

        for (Map.Entry<Integer, Trainer> opponent : opponents.entrySet()) {
            // check each elite 4 if they have pokemon alive
            boolean allFainted = true;
            for (Pokemon pokemon : opponent.getValue().getPokemon().values()) {
                if (!pokemon.isFainted()) {
                    allFainted = false;
                    hasNoPokemon = false;
                    activeOpponent = opponent.getValue();
                    opponentPokemon = activeOpponent.getPokemon().get(1);
                    System.out.printf("%s enters the arena!", activeOpponent.getName());
                    pause(2_000);
                    blankLines(1);
                    battle();
                    break;
                }
            }
            if (!allFainted) {
                break;
            }
        }
        // fires when all the elite 4 are defeated
        if (hasNoPokemon && (surprise.getPokemon().get(1).isFainted())) {
            pause(2_000);
            blankLines(1);
            battleOver();
        }
        if (hasNoPokemon) {
            System.out.println("Congratulations!!! You defeated the Elite 4!\n");
            pause(2_000);
            blankLines(1);
            thoseWhoFightFurther();
        }
    }

    private void thoseWhoFightFurther() {
        System.out.println("But suddenly you hear a voice...");
        pause(2_000);
        System.out.println("What's this?");
        pause(2_000);
        System.out.println("A real challenge?");
        pause(2_000);
        System.out.println("The current champion enters the stage");
        pause(2_000);
        activeOpponent = surprise;
        opponentPokemon = surprise.getPokemon().get(1);
        System.out.printf("%s approaches you.\n", activeOpponent.getName());
        pause(1_000);
        battle();
    }

    //should go to game over when all of your pokemon are dead
    private void pokeSwitch() {
        clear();

        System.out.println("  Option     Pokemon       Level    HP    isFainted");
        System.out.println("  ======   ============    =====   ====   =========");
        for (Map.Entry<Integer, Pokemon> pokemon : trainer.getPokemon().entrySet()) {
            System.out.printf("%5s:      %-10s      %s      %-5s   %s\n",
                    pokemon.getKey(), pokemon.getValue().getName(),
                    pokemon.getValue().getLevel(), pokemon.getValue().getHitPoints(),
                    pokemon.getValue().isFainted());
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
        else {
            for (int i = 0; i < trainer.getPokemon().size(); i++) {
                String pokemonPrompt = prompter.prompt("Select pokemon #: ", "1|2|3|4", "\nThis is not a valid option!\n");
                Pokemon selectedPokemon = trainer.getPokemon().get(Integer.parseInt(pokemonPrompt));
                if (!selectedPokemon.isFainted()) {
                    activePokemon = selectedPokemon;
                    System.out.printf("You selected %s.\n", activePokemon.getName());
                    pause(2_000);
                    blankLines(1);
                    battle();
                    break;
                } else {
                    i--;
                    System.out.println("Cannot select pokemon\n");
                    pause(2_000);
                    blankLines(1);
                }
            }
        }
    }

    private void opponentTurn() {   // TODO: might need to adjust damage for difficulty
        int opponentAttack = opponentPokemon.attack(activePokemon);
        System.out.printf("%s used %s\n", opponentPokemon.getName(), opponentPokemon.getAttack());
        System.out.printf("Deals %s points of damage to %s\n", opponentAttack, activePokemon.getName());
        Console.blankLines(1);
        pause(2_000);

        if (activePokemon.getHitPoints() <= 0) {  // if your pokemon fainted, automatically call switch pokemon
            System.out.printf("%s fainted from battle.\n", activePokemon.getName());
            blankLines(1);
            activePokemon.setFainted(true);
            activePokemon.setHitPoints(0);
            inBattle = false;
            pause(2_000);
            switchPokemon();
        }
    }

    private void setMaxHP(){
        maxHP1 = trainer.getPokemon().get(1).getHitPoints();
        maxHP2 = trainer.getPokemon().get(2).getHitPoints();
        maxHP3 = trainer.getPokemon().get(3).getHitPoints();
        maxHP4 = trainer.getPokemon().get(4).getHitPoints();
    }

//    private int pullMaxHP() {
//        for (Pokemon pokemon : trainer.getPokemon().values()) {
//            if () {
//
//            }
//        }
//    }

}