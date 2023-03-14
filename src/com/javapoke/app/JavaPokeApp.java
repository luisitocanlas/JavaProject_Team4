package com.javapoke.app;

import com.javapoke.Pokemon;
import com.javapoke.Trainer;
import com.apps.util.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static com.apps.util.Console.*;

public class JavaPokeApp implements SplashApp {
    private static final int maxLength = 12;
    private static final String pokemonData = "data/Pokemon Chart.csv";

    private final Map<Integer, Pokemon> pokemonMap = loadPokemonMap();
    private final Prompter prompter = new Prompter(new Scanner(System.in));
    private final Introduction intro = new Introduction(prompter);
    private Trainer player;

    /*
     * TODO: work on chooseTrainer(), characterCreationPrompt(), and trainerCollectionPrompt()
     * TODO: input trainer_selection.txt onto chooseTrainer()
     * TODO: Create tests for prompts
     */

    /*
     * This method will be the main method that will run the game through the controller
     */
    public void beginChallenge() {
        intro.startUp();
        chooseTrainer();    // Completed
        choosePokemon();
        //startGame();
        gameOver();
    }

    @Override
    public void start() {
        beginChallenge();
    }

    /*
     * This method will produce a GAME OVER message to the user if called.
     */
    private void gameOver() {
        clear();
        try {
            Files.readAllLines(Path.of("images/gameOver.txt")).forEach(System.out::println);
            Console.pause(3000);
            Files.readAllLines(Path.of("images/thank_you_message.txt"))
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startGame() {
        clear();
    }

    /*
     * This method will allow the user to choose from a Map of Pokémon and depending on their input
     * will add the Pokémon to their arsenal. Will need to make sure that the player may not choose
     * the same Pokémon on the list.
     */
    // TODO work on the format of Eevee and Gengar #1 and #10
    private void choosePokemon() {
        clear();
//        List<Pokemon> trainerPokemon = new LinkedList<>();
        Map<Integer, Pokemon> trainerPokemon = new HashMap<>();         // changed to map instead of list
        System.out.println(" Option \tPokemon \tLevel \tHP");
        System.out.println(" ====== \t======= \t===== \t===");
        for (Map.Entry<Integer, Pokemon> entry : pokemonMap.entrySet()) {
            System.out.printf("   %s: \t\t%s \t %s \t%s\n"
                    , entry.getKey(), entry.getValue().getName()
                    , entry.getValue().getLevel(), entry.getValue().getHitPoints());
        }
        Console.blankLines(2);
        System.out.println("You may choose 4 pokemon to join you in your journey");
        for (int i = 0; i < 4; i++) {
            String pokemonPrompt = prompter.prompt("Select pokemon #" + (i + 1) + ": "
                    , "^[1-9]|10$", "\nThis is not a valid option!\n");
            if (!trainerPokemon.containsValue(pokemonMap.get(Integer.parseInt(pokemonPrompt)))) {
//                trainerPokemon.add(i,pokemonMap.get(Integer.parseInt(pokemonPrompt)));
                trainerPokemon.put(i + 1, pokemonMap.get(Integer.parseInt(pokemonPrompt)));   // from the change above
            } else {
                System.out.println("Can not choose duplicate Pokemon for this challenge.");
                i--;
            }
        }
        player.setPokemon(trainerPokemon);
        System.out.println(player);
    }

    private Map<Integer, Pokemon> loadPokemonMap()
    throws RuntimeException {
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
        clear();
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
        clear();

        List<Trainer> trainers = new ArrayList<>(List.of(new Trainer("Ash")
                , new Trainer("Brock"), new Trainer("Misty")));

        int optionNumber = 1;
        for (Trainer value : trainers) {
            System.out.println(optionNumber + ". " + value.getName());
            optionNumber++;
        }

        //TODO: ^[1-4]$ for inputs 1-4 [1. Ash 2. Misty 3. Brock 4. Create your own Trainer]
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
        clear();
        String characterName = prompter.prompt("What is the name of your Trainer: ", "^.{1,12}$"
                , "\nName must not exceed 12 characters!\n");
        player = new Trainer(characterName);
    }
}