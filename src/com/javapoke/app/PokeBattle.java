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


class PokeBattle {

    private final Prompter prompter;
    private final Map<String, Integer> maxHpContainer = new TreeMap<>();
    private final EliteTrainer lorelei = new EliteTrainer().loadLorelei();
    private final EliteTrainer bruno = new EliteTrainer().loadBruno();
    private final EliteTrainer agatha = new EliteTrainer().loadAgatha();
    private final EliteTrainer lance = new EliteTrainer().loadLance();
    private final Map<Integer, Trainer> opponents = new TreeMap<>(Map.of(1, lorelei, 2, bruno, 3, agatha, 4, lance));
    private final Trainer surprise = surprise();

    private static String battleMenu;
    private static String runAwayBanner;
    private static String battleOverBanner;
    private static String congratulationsBanner;
    private static String continuePrompt;

    private int potion = 10;
    private int superPotion = 5;
    private int maxHP;
    private boolean inBattle = false;
    private Pokemon activePokemon;
    private Trainer trainer;
    private Trainer activeOpponent = opponents.get(1);
    private boolean isGameOver = false;
    private Pokemon opponentPokemon = activeOpponent.getPokemon().get(1);

    PokeBattle(Prompter prompter) {
        this.prompter = prompter;
    }

    // Business Methods()

    // this method is for getting the data from the prompts and storing them in the containers
    void startPokeBattle(Trainer player) {
        trainer = player;
        // container for the active pokemon for the current battle, should be getting from user
        activePokemon = trainer.getPokemon().get(1);
        setMaxHP(); // sets maxHP used for healing your Pokemon

        // go to battle!
        showTrainer();
        showOpponentPokemon();
        showPlayerPokemon();
        pause(2_500);
        battle();
    }

    private void battleOver() {
        isGameOver = true;
        pause(3_000);
        clear();

        System.out.println(battleOverBanner);
        pause(3_500);
        clear();

        System.out.println(congratulationsBanner);
        pause(3_500);
    }

    private void battle() {
        clear();
        while (!isGameOver) {
            inBattle = true;

            // show opponentPokemon details here
            System.out.printf("Trainer: %s\n", activeOpponent.getName());
            System.out.printf("Pokemon: %-14s   HP: %s", opponentPokemon.getName(), opponentPokemon.getHitPoints());
            blankLines(2);
            // show activePokemon details here
            System.out.printf("Trainer: %s\n", trainer.getName());
            System.out.printf("Pokemon: %-14s   HP: %s ", activePokemon.getName(), activePokemon.getHitPoints());
            blankLines(1);

            System.out.println(battleMenu);
            blankLines(1);

            String battlePrompt = prompter.prompt("\t What will you do? ", "1|2|3|4", "\n\t This is not a valid option!\n");
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
        // ask the player which potion to use
        System.out.println("    Option                 Heals HP by:    Qty:");
        System.out.println("    ======                 ============    ====");
        System.out.printf("[1] Potion                      50    %8s\n", potion);
        System.out.printf("[2] Super potion               100          %2s\n", superPotion);
        System.out.println("[3] Back to main battle\n");
        String prompt = prompter.prompt("Pick your poison: ", "1|2|3", "\nThis is not a valid option!\n");

        if (potion >= 1 && Integer.parseInt(prompt) == 1) {
            pullMaxHP();    // gets the max hp for the active pokemon
            if (activePokemon.getHitPoints() > maxHP) {
                activePokemon.setHitPoints(maxHP);
            } else if (activePokemon.getHitPoints() == maxHP) {
                blankLines(1);
                System.out.println(activePokemon.getName() + "'s health is full. You can not use a Potion");
            } else if (activePokemon.getHitPoints() + POTION.getValue() > maxHP) {
                potion--;
                activePokemon.setHitPoints(maxHP);
                System.out.printf("You used Potion! Potion remaining: %s\n", potion);
                System.out.printf("%s healed by %s points\n", activePokemon.getName(), POTION.getValue());
            } else {
                potion--;
                activePokemon.setHitPoints(activePokemon.getHitPoints() + POTION.getValue());
                System.out.printf("You used Potion! Potion remaining: %s\n", potion);
                System.out.printf("%s healed by %s points\n", activePokemon.getName(), POTION.getValue());
            }
        } else if (superPotion >= 1 && Integer.parseInt(prompt) == 2) {
            pullMaxHP();    // gets the max hp for the active pokemon
            if (activePokemon.getHitPoints() > maxHP) {
                activePokemon.setHitPoints(maxHP);
            } else if (activePokemon.getHitPoints() == maxHP) {
                blankLines(1);
                System.out.println(activePokemon.getName() + "'s health is full. You can not use a Super Potion");
            } else if (activePokemon.getHitPoints() + SUPER_POTION.getValue() > maxHP) {
                superPotion--;
                activePokemon.setHitPoints(maxHP);
                System.out.printf("You used Super Potion! Super Potion remaining: %s\n", superPotion);
                System.out.printf("%s healed by %s points\n", activePokemon.getName(), SUPER_POTION.getValue());
            } else {
                superPotion--;
                activePokemon.setHitPoints(activePokemon.getHitPoints() + SUPER_POTION.getValue());
                System.out.printf("You used Super Potion! Super Potion remaining: %s\n", superPotion);
                System.out.printf("%s healed by %s points\n", activePokemon.getName(), SUPER_POTION.getValue());
            }
        } else if (Integer.parseInt(prompt) == 3) {
            battle();
        } else if (potion == 0 && superPotion == 0) {
            System.out.println("\nYou have ran out of potions!\n");
        }
        pause(3_500);
        // go back to main battle options
        battle();
    }

    private void switchPokemon() {   // for future implementation, add the option to switch pokemon in between battles
        clear();
        System.out.println("Choose a Pokemon.");
        blankLines(1);
        // if in a battle [inBattle = true]
        if (!inBattle) {
            // will output the list of pokemon available, their LVL and HP, and user will select from the list
            pokeSwitch();
            // computer attacks your pokemon
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
        System.out.println(runAwayBanner);
        pause(2_500);
        isGameOver = true;
    }

    private void nextPokemon() {     // just for the current opponent's pokemon
        clear();
        boolean allFainted = true;
        for (Pokemon pokemon : activeOpponent.getPokemon().values()) {
            if (!pokemon.isFainted()) {
                allFainted = false;
                opponentPokemon = pokemon;
                showOpponentPokemon();
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
            } else {                                                    // goes to continue or give up
                System.out.printf("Congratulations on defeating %s!", activeOpponent.getName());
                pause(2_000);
                blankLines(1);
                fightOn();
            }
        }
    }

    private void fightOn() { // ask the player if they want to continue fighting or give up.
        clear();
        System.out.println(continuePrompt);
        String continuePrompt = prompter.prompt("\t What will you do? ", "1|2", "\n\t This is not a valid option!\n");
        switch (Integer.parseInt(continuePrompt)) {
            case 1:
                nextOpponent();
                break;
            case 2:
                runAway();
                break;
        }
    }

    private void nextOpponent() {    // just for switching the opponents' pokemon
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
                    showTrainer();
                    showOpponentPokemon();
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
        // fires when all the elite 4 are defeated and the surprise champion is defeated
        if (hasNoPokemon && (surprise.getPokemon().get(1).isFainted())) {
            pause(2_000);
            blankLines(1);
            battleOver();
        }
        // fires when all the elite 4 are defeated
        if (hasNoPokemon) {
            System.out.println("Congratulations!!! You defeated the Elite 4!\n");
            pause(2_000);
            blankLines(1);
            thoseWhoFightFurther();
        }
    }

    private void thoseWhoFightFurther() {   // fires when you defeat the elite four and continue with the game
        activeOpponent.loadDialogue("Surprise");
        activeOpponent = surprise;
        opponentPokemon = surprise.getPokemon().get(1);
        clear();
        showTrainer();
        pause(3_500);
        clear();
        showOpponentPokemon();
        battle();
    }

    private Trainer surprise() {    // the actual champion
        Trainer josh = null;
        try {
            Pokemon mewtwo = new Pokemon("Mewtwo", 65, 250, "Psychic",
                    Files.readString(Path.of("images/elite4_pokemon/Mewtwo_dark.txt")));
            josh = new Trainer("THE Joshua BLOCH", Map.of(1, mewtwo));
            return josh;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return josh;
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
            System.out.println("\t All of your Pokemon fainted from battle");
            pause(2_000);
            blankLines(1);
            runAway();
        } else {
            for (int i = 0; i < trainer.getPokemon().size(); i++) {
                String pokemonPrompt = prompter.prompt("Select pokemon #: ", "1|2|3|4", "\nThis is not a valid option!\n");
                Pokemon selectedPokemon = trainer.getPokemon().get(Integer.parseInt(pokemonPrompt));
                if (!selectedPokemon.isFainted()) {
                    activePokemon = selectedPokemon;
                    showPlayerPokemon();
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

    private void opponentTurn() {   // might need to adjust damage for difficulty
        int opponentAttack = opponentPokemon.attack(activePokemon);
        System.out.printf("%s used %s\n", opponentPokemon.getName(), opponentPokemon.getAttack());
        System.out.printf("Deals %s points of damage to %s\n", opponentAttack, activePokemon.getName());
        Console.blankLines(1);
        pause(2_000);
        clear();

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

    private void setMaxHP() {
        // get max hp of each selected pokemon
        int maxHP1 = trainer.getPokemon().get(1).getHitPoints();
        int maxHP2 = trainer.getPokemon().get(2).getHitPoints();
        int maxHP3 = trainer.getPokemon().get(3).getHitPoints();
        int maxHP4 = trainer.getPokemon().get(4).getHitPoints();
        // container for pokemon max hp
        maxHpContainer.put(trainer.getPokemon().get(1).getName(), maxHP1);
        maxHpContainer.put(trainer.getPokemon().get(2).getName(), maxHP2);
        maxHpContainer.put(trainer.getPokemon().get(3).getName(), maxHP3);
        maxHpContainer.put(trainer.getPokemon().get(4).getName(), maxHP4);
    }

    private void pullMaxHP() {
        for (Map.Entry<String, Integer> pokemon : maxHpContainer.entrySet()) {
            if (activePokemon.getName().equals(pokemon.getKey())) {
                maxHP = pokemon.getValue();
                break;
            }
        }
    }

    private void showOpponentPokemon() {
        System.out.printf("%s summons %s.\n", activeOpponent.getName(), opponentPokemon.getName());
        pause(2_500);
        clear();
        System.out.println(opponentPokemon.getArt());
        pause(5_000);
        clear();
    }

    private void showPlayerPokemon() {
        System.out.printf("You summon %s.\n", activePokemon.getName());
        pause(2_500);
        clear();
        System.out.println(activePokemon.getArt());
        pause(5_000);
        clear();
    }

    private void showTrainer() {
        System.out.printf("%s enters the arena!", activeOpponent.getName());
        pause(2_500);
        clear();
        try {
            Files.readAllLines(Path.of("images/elite4_trainers/" + activeOpponent.getName() + ".txt"))
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
        pause(3_500);
        clear();
        activeOpponent.loadDialogue(activeOpponent.getName());
    }

    static{
        try {
            battleMenu = Files.readString(Path.of("images/battle_Prompt.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            runAwayBanner = Files.readString(Path.of("images/runAwayBanner.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            battleOverBanner = Files.readString(Path.of("images/battleOver_Banner.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            congratulationsBanner = Files.readString(Path.of("images/congratulations_banner.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            continuePrompt = Files.readString(Path.of("images/continue_Prompt.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}