package com.javapoke.app;

import com.javapoke.Pokemon;
import com.javapoke.Trainer;
import com.apps.util.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class JavaPokeApp implements SplashApp {
    private static final int maxLength = 12;
    private static final String pokemonData = "data/Pokemon Chart.csv";

    private final Map<Integer, Pokemon> pokemonMap = loadPokemonMap();
    private final Prompter prompter = new Prompter(new Scanner(System.in));
    private Trainer player;

    /*
     * This method will be the main method that will run the game through the controller
     */

    @Override
    public void start() {
        beginChallenge();
    }

    @Override
    public void welcome(String... strings) throws IllegalArgumentException {
        SplashApp.super.welcome(strings);
    }

    public void beginChallenge() {
        welcomePrompt();    // Completed
        chooseTrainer();    // Completed
        choosePokemon();
        //startGame();
        gameOver();
    }

    private void welcomeMessage() {
//        welcome("Poke.png", "credits.png");
        System.out.println("W E L C O M E  T O  J A V A  P O K E!!!!");
    }

    /*
     * This method will welcome the user to the game and prompt the user if they would like
     * to read the rules or not before starting the game. If the player inputs "Y/y" they
     * will be shown the rules(). If the player inputs "N/n" the loop will break
     * and continue to chooseTrainer() in beginChallenge()
     */
    private void welcomePrompt() {
        welcomeMessage();
        Console.blankLines(1);

        String input = prompter.prompt("Would you like to see the rules before starting our game? " +
                "Enter [Y]es or [N]o : ", "Y|N|y|n", "\nThis is not a valid option!\n");
        if ("Y".equalsIgnoreCase(input)) {
            rules();
        }
    }

    /*
     * This method will produce the list of Rules and Objectives of the game.
     */
    private void rules() {
        Console.blankLines(1);
        System.out.println("Objective: Defeat the Elite Four and be the #1 Pokemon Trainer");
        Console.blankLines(1);
        System.out.println("\t\t\t\t\t\t\tR U L E S:");
        System.out.println("1. Using an item or switching Pokemon during battle " +
                "will consume your turn.");
        System.out.println("2. You'll be given a fixed amount of "
                + "Potions and Super Potions (items).");
        System.out.println("3. Your items will not regenerate after a battle is completed.");
        System.out.println("4. If all your pokemon have fainted. You lose!!!");
        System.out.println("5. The first Pokemon you pick will be 1st active in battle");
    }

    /*
     * This method will produce a GAME OVER message to the user if called.
     */
    private void gameOver() {
        Console.clear();
        System.out.println("\t\tG A M E  O V E R  ! ! ! !");
        System.out.println("T H A N K  Y O U  F O R  P L A Y I N G  !");
    }

    private void startGame() {
        Console.clear();
    }

    /*
     * This method will allow the user to choose from a Map of Pokémon and depending on their input
     * will add the Pokémon to their arsenal. Will need to make sure that the player may not choose
     * the same Pokémon on the list.
     */
    private void choosePokemon() {
        Console.clear();
        List<Pokemon> trainerPokemon = new LinkedList<>();
        System.out.println(" Option \tPokemon \tLevel \tHP");
        System.out.println(" ====== \t======= \t===== \t===");
        for (Map.Entry<Integer, Pokemon> entry : pokemonMap.entrySet()) {
            System.out.printf("   %s: \t\t%s \t %s \t%s\n"
                    ,entry.getKey(),entry.getValue().getName()
                    ,entry.getValue().getLevel(),entry.getValue().getHitPoints());
        }
        Console.blankLines(2);
        System.out.println("You may choose 4 pokemon to join you in your journey");
        for (int i = 0; i < 4; i++) {
            String pokemonPrompt = prompter.prompt("Select pokemon #" + (i+1) + ": "
                    , "^[1-9]|10$", "\nThis is not a valid option!\n");
            if(!trainerPokemon.contains(pokemonMap.get(Integer.parseInt(pokemonPrompt)))) {
                trainerPokemon.add(i,pokemonMap.get(Integer.parseInt(pokemonPrompt)));
            }
            else {
                System.out.println("Can not choose duplicate Pokemon for this challenge.");
                i--;
            }
        }
        player.setPokemon(trainerPokemon);
        System.out.println(player);
    }

    private Map<Integer, Pokemon> loadPokemonMap() {
        Map<Integer, Pokemon> pokemonMap = new HashMap<>();

        try {
            List<String> lines = Files.readAllLines(Path.of(pokemonData));

            for (String line : lines) {
                String[] tokens = line.split(",");
                if (tokens.length != 4) {
                    throw new RuntimeException("Invalid Line in CSV file " + line);
                }
                pokemonMap.put(Integer.valueOf(tokens[0]),
                        new Pokemon(tokens[1], Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pokemonMap;
    }

    private void chooseTrainer() {
        Console.clear();
        System.out.println("T R A I N E R  S E L E C T I O N:");
        System.out.println("If you'd like to create your own Trainer. Enter 1");
        System.out.println("If you'd like to choose a Trainer. Enter 2");
        String input = prompter.prompt("What choice would you like to choose: ", "1|2"
                , "\nThis is not a valid option!\n");
        if (Integer.parseInt(input) == 1) {
            characterCreationPrompt();
        } else {
            trainerCollectionPrompt();
        }
        System.out.println(player);
    }

    private void trainerCollectionPrompt() {
        Console.clear();

        List<Trainer> trainers = new ArrayList<>(List.of(new Trainer("Ash")
                , new Trainer("Brock"), new Trainer("Misty")));

        int optionNumber = 1;
        for (Trainer value : trainers) {
            System.out.println(optionNumber + ". " + value.getName());
            optionNumber++;
        }

        String prompt = prompter.prompt("Which Trainer would you like to choose: ", "1|2|3",
                "\nThis is not a valid option!\n");
        if (Integer.parseInt(prompt) == 1) {
            player = trainers.get(0);
        } else if (Integer.parseInt(prompt) == 2) {
            player = trainers.get(1);
        } else {
            player = trainers.get(2);
        }
    }

    // Fun Fact: Original game had a limit of 10 characters.
    private void characterCreationPrompt() {
        Console.clear();
        String characterName = prompter.prompt("What is the name of your Trainer: ", "^.{1,12}$"
                , "\nName must not exceed 12 characters!\n");
        player = new Trainer(characterName);
    }

    public void dumpPokemonMap() {
        System.out.println(pokemonMap);
    }
}