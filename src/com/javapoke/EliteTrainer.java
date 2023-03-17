package com.javapoke;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is an extension of Trainer and is used to create Elite Trainers
 * that are used throughout the program.
 *
 * @author Jorge Aponte and Lui Canlas
 * @version 1.0
 */
public class EliteTrainer extends Trainer {

    public EliteTrainer() {
    }

    public EliteTrainer(String name, Map<Integer, Pokemon> pokemon) {
        super(name, pokemon);
    }

    /**
     * This method will create an instance of an Elite Trainer that is named Agatha
     * @return Elite Trainer named Agatha and their respective Map of Pokemon.
     */
    public EliteTrainer loadAgatha() {
        String name = "Agatha";
        return new EliteTrainer(name, loadElitePokemon(name));
    }

    /**
     * This method will create an instance of an Elite Trainer that is named Bruno
     * @return Elite Trainer named Bruno and their respective Map of Pokemon.
     */
    public EliteTrainer loadBruno() {
        String name = "Bruno";
        return new EliteTrainer(name, loadElitePokemon(name));
    }

    /**
     * This method will create an instance of an Elite Trainer that is named Lorelei
     * @return Elite Trainer named Lorelei and their respective Map of Pokemon.
     */
    public EliteTrainer loadLorelei() {
        String name = "Lorelei";
        return new EliteTrainer(name, loadElitePokemon(name));
    }

    /**
     * This method will create an instance of an Elite Trainer that is named Lance
     * @return Elite Trainer named Lance and their respective Map of Pokemon.
     */
    public EliteTrainer loadLance() {
        String name = "Lance";
        return new EliteTrainer(name, loadElitePokemon(name));
    }

    private Map<Integer, Pokemon> loadElitePokemon(String name)
    throws RuntimeException {
        String eliteTrainerPath = "data/Elite Trainer " + name + " Pokemon.csv";
        Map<Integer, Pokemon> pokemonMap = new HashMap<>();

        try {
            List<String> lines = Files.readAllLines(Path.of(eliteTrainerPath));

            for (String line : lines) {
                String[] tokens = line.split(",");
                if (tokens.length != 6) {
                    throw new RuntimeException("Invalid Line in CSV file " + line);
                }
                pokemonMap.put(Integer.valueOf(tokens[0]),
                        new Pokemon(tokens[1], Integer.parseInt(tokens[2]),
                                Integer.parseInt(tokens[3]), tokens[4],
                                Files.readString(Path.of(tokens[5]))));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pokemonMap;
    }
}