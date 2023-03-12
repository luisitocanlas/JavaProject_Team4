package com.javapoke.app;

import com.javapoke.Pokemon;
import com.javapoke.Trainer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class JavaPokeApp {
    private static final int maxLength = 12;
    private static final String pokemonData = "data/Pokemon Chart.csv";
    private final Map<Integer,Pokemon> pokemonMap = loadPokemonMap();

    private Trainer trainer;
    private final Scanner scanner = new Scanner(System.in);

    /*
     * This method will be the main method that will run the game through the controller
     */
    public void beginChallenge() {
        welcome();
        chooseTrainer();
        choosePokemon();
        startGame();
        gameOver();
    }

    /*
     * This method will welcome the user to the game and prompt the user if they would like
     * to read the rules or not before starting the game. If the player inputs "Y/y" they
     * will be shown the rules(). If the player inputs "N/n" the loop will break
     * and continue to chooseTrainer() in beginChallenge()
     */
    private void welcome() {
        boolean validInput = false;

        while (!validInput) {
            System.out.println("W E L C O M E  T O  J A V A  P O K E!!!!");
            System.out.print("Would you like to see the rules before starting our game? " +
                    "Enter [Y]es or [N]o : ");
            String ruleInput = scanner.nextLine().trim().toUpperCase();
            if (ruleInput.matches("Y|N")) {
                validInput = true;
                if ("Y".equals(ruleInput)) {
                    rules();
                } else {
                    break;
                }
            }
        }
    }

    /*
     * This method will produce the list of Rules and Objectives of the game.
     */
    private void rules() {
        System.out.println("Objective: Defeat the Elite Four and be the #1 Pokemon Trainer");
        System.out.println("R U L E S");
        System.out.println("1. When you use an item or switch Pokemon during battle you " +
                "will consume your turn and are open to be attacked");
        System.out.println("2. In your items bag you will have a fixed amount of "
                + "Potions and Super Potions. Use Wisely.");
        System.out.println("3. Your items will not regenerate after a battle is completed");
        System.out.println("4. If all your pokemon have fainted. You lose!!!");
    }

    /*
     * This method will produce a GAME OVER message to the user if called.
     */
    private void gameOver() {
//        console.clear();
        System.out.println("G A M E  O V E R  ! ! ! !");
        System.out.println("T H A N K  Y O U  F O R  P L A Y I N G  !");
    }

    private void startGame() {
//        console.clear();
    }

    /*
     * This method will allow the user to choose from a Map of Pokémon and depending on their input
     * will add the Pokémon to their arsenal. Will need to make sure that the player may not choose
     * the same Pokémon on the list.
     */
    private void choosePokemon() {
//        console.clear();
        boolean validInput = false;

        while (!validInput) {
            System.out.println("You may choose 4 pokemon to join you in your journey");
            System.out.println("Select your 1st pokemon : ");
            // Here we will display the Map<Integer,Pokemon>    1-10   |   Pokemon Obj
            loadPokemonMap();


        }
    }

    private Map<Integer, Pokemon> loadPokemonMap () {
        Map<Integer, Pokemon> pokemonMap = new HashMap<>();

        try {
            List<String> lines = Files.readAllLines(Path.of("data/Pokemon Chart.csv"));

            for (String line : lines) {
                String[] tokens = line.split(",");
                if (tokens.length >= 4) {
                    pokemonMap.put(Integer.valueOf(tokens[0]),
                            new Pokemon(tokens[1],Integer.parseInt(tokens[2]),Integer.parseInt(tokens[3])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pokemonMap;
    }

    private static Pokemon getPokemon(String line) {
        String[] tokens = line.split(",");
        if (tokens.length != 3) {
            throw new RuntimeException("Invalid Line in CSV file " + line);
        }
        return new Pokemon(tokens[1],Integer.parseInt(tokens[2]),Integer.parseInt(tokens[3]));
    }

    private void chooseTrainer() {
//        console.clear();
        boolean validInput = false;

        while (!validInput) {
            System.out.println("T R A I N E R  S E L E C T I O N:");
            System.out.println("If you'd like to create your own Trainer. Enter 1");
            System.out.println("If you'd like to choose a Trainer. Enter 2");
            System.out.print("What choice would you like to choose: ");
            String inputTrainer = scanner.nextLine().trim();
            if (inputTrainer.matches("\\d{1}")) {
                int input = Integer.parseInt(inputTrainer);
                if (input == 1) {
                    validInput = true;
                    characterCreation();
                } else if (input == 2) {
                    validInput = true;
                    trainerCollection();    // Print out the trainer collection to choose from
                }
            }
        }
    }

    private void trainerCollection() {
//        console.clear();

        boolean validInput = false;

        while (!validInput) {
            // Showcase the collections of Trainers to choose from
            System.out.println("Which Trainer would you like to choose: ");
        }
    }

    private void characterCreation() {
//        console.clear();

        boolean validInput = false;

        while (!validInput) {
            System.out.println("What is the name of your Trainer: ");
            String playerName = scanner.nextLine();
            if (playerName.length() <= maxLength) {
                Trainer player = new Trainer(playerName);
                validInput = true;
            } else {
                System.out.printf("Name must not exceed %s characters", maxLength);
            }
        }
    }

    public void dumpPokemonMap() {
        System.out.println(pokemonMap);
    }
}