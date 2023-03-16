package com.javapoke;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EliteTrainer extends Trainer {

    public EliteTrainer() {
    }

    public EliteTrainer(String name, Map<Integer, Pokemon> pokemon) {
        super(name, pokemon);
    }

    public EliteTrainer loadAgatha() {
        String name = "Agatha";
        return new EliteTrainer(name, loadElitePokemon(name));
    }

    public EliteTrainer loadBruno() {
        String name = "Bruno";
        return new EliteTrainer(name, loadElitePokemon(name));
    }

    public EliteTrainer loadLorelei() {
        String name = "Lorelei";
        return new EliteTrainer(name, loadElitePokemon(name));
    }

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