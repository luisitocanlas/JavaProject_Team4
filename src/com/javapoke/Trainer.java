package com.javapoke;

import java.util.Map;

/**
 * This class is for creation of a Trainer and loading their respective dialogue
 *
 * @author Jorge Aponte and Lui Canlas
 * @version 1.0
 */
public class Trainer {
    //fields
    private String name;
    private Map<Integer, Pokemon> pokemon;
    private final Dialogue dialogue = new Dialogue();

    //constructors
    public Trainer() {
    }

    public Trainer(String name) {
        this.name = name;
    }

    public Trainer(String name, Map<Integer, Pokemon> pokemon) {
        this.name = name;
        this.pokemon = pokemon;
    }

    /**
     * This method loads the dialogue for the specified character name.
     *
     * @param name the name of the character whose dialogue to load.
     *             Must be one of: "Agatha", "Bruno", "Lorelei", "Lance", "THE Joshua BLOCH", "Surprise".
     */
    public void loadDialogue(String name) {
        switch (name) {
            case "Agatha":
                dialogue.agathaDialogue();
                break;
            case "Bruno":
                dialogue.brunoDialogue();
                break;
            case "Lorelei":
                dialogue.loreleiDialogue();
                break;
            case "Lance":
                dialogue.lanceDialogue();
                break;
            case "THE Joshua BLOCH":
                dialogue.joshDialogue();
                break;
            case "Surprise":
                dialogue.surpriseDialogue();
                break;
        }
    }

    //accessors
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Integer, Pokemon> getPokemon() {
        return pokemon;
    }

    public void setPokemon(Map<Integer, Pokemon> pokemon) {
        this.pokemon = pokemon;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": name=" + name + ", pokemon=" + pokemon;
    }
}