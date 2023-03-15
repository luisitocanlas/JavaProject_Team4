package com.javapoke.app;

import com.apps.util.Prompter;
import com.javapoke.Pokemon;
import com.javapoke.Trainer;

import java.util.*;

import static com.apps.util.Console.*;

import static com.javapoke.Potion.*;


public class PokeBattle {
    private final Prompter prompter;
    boolean inBattle = false;               // will be a check when switching pokemon
    Map<Integer, Pokemon> pokeStorage;      // this will be connected to the player's selection

    // Potion Counter
    int potion = 10;
    int superPotion = 5;

    /*
     * Code within the dashes are used for method functionality, may keep some of them----------------------------------
     */
    // create pokemon and add the attacks
    Pokemon charmander = new Pokemon("Charmander", 62, 165, "Ember");
    Pokemon squirtle = new Pokemon("Squirtle", 60, 145, "Water ball");
    Pokemon lapras = new Pokemon("Lapras", 60, 130, "Blizzard");
    Pokemon machamp = new Pokemon("Machamp", 60, 90, "Body Slam");
    Pokemon gengar = new Pokemon("Gengar", 60, 60, "Night Shade");
    Pokemon dragonite = new Pokemon("Dragonite", 60, 91, "Hyper Beam");
    Pokemon mewtwo = new Pokemon("Mewtwo", 60, 106, "Psychic");

    // create a pokemon storage that the trainer carries, might change this to pokeStorage
    Map<Integer, Pokemon> pokeBelt = Map.of(1, charmander, 2, squirtle);

    // create player and opponents
    Trainer player = new Trainer("Ash", pokeBelt);              // will be replaced by the chooserTrainer()
    Trainer elite1 = new Trainer("Lorelei", Map.of(1, lapras));
    Trainer elite2 = new Trainer("Bruno", Map.of(1,machamp));
    Trainer elite3 = new Trainer("Agatha", Map.of(1,gengar));
    Trainer elite4 = new Trainer("Lance", Map.of(1,dragonite));
    Trainer surprise = new Trainer("THE Joshua BLOCH", Map.of(1,mewtwo));
    //------------------------------------------------------------------------------------------------------------------

    /*
     * Containers-------------------------------------------------------------------------------------------------------
     */
    // container for the active pokemon for the current battle
    Pokemon activePokemon = player.getPokemon().get(0);

    // list of opponents to go through, data will be from csv
    Map<Integer, Trainer> opponents = Map.of(1,elite1, 2,elite2, 3,elite3, 4,elite4, 5,surprise);

    // container for active opponent, and their active pokemon, will initialize for the first opponent
    Trainer activeOpponent = elite1;
    Pokemon opponentPokemon = activeOpponent.getPokemon().get(1);

    // ctor
    public PokeBattle(Prompter prompter) {
        this.prompter = prompter;
    }

    /*
     * Main methods-----------------------------------------------------------------------------------------------------
     */
    public void startPokeBattle() {
        clear();
        inBattle = true;
        // show activePokemon details here
        System.out.println(activePokemon);
        // show opponentPokemon details here
        System.out.println(opponentPokemon);

        String battlePrompt = prompter.prompt("What will you do?", "1|2|3|4", "\nThis is not a valid option!\n");
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
        System.out.printf("%s uses %s\n", activePokemon.getName(), activePokemon.getAttack());
        System.out.printf("Deals %s points of damage to %s\n", playerAttack, opponentPokemon.getName());

        // opponent attacks
        opponentTurn();

        // check for hit points for both sides
        if (activePokemon.getHitPoints() <= 0) {        // if your pokemon fainted, automatically call switch pokemon
            System.out.printf("%s fainted from battle.", activePokemon.getName());
            activePokemon.setFainted(true);
            inBattle = false;
            switchPokemon();
        }
        else if (opponentPokemon.getHitPoints() <= 0) { // if your opponent's pokemon fainted
            System.out.printf("%s's %s fainted from battle", activeOpponent.getName(), opponentPokemon.getName());
            inBattle = false;
            nextPokemon();
        }
        //end of turn
        startPokeBattle();
    }

    public void useItem() {
        clear();
        // ask the player which potion to use
        String prompt = prompter.prompt("Select [1] for Potion or [2] for Super Potion: ", "1|2",
                "\nThis is not a valid option!\n");
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
        // computer attacks your pokemon
        opponentTurn();
        // go back to main battle options
        startPokeBattle();
    }

    public void switchPokemon() {
        clear();
        System.out.println("Choose a Pokemon.");
        // if in a battle [inBattle = true]
        if (!inBattle) {
            // will output the list of pokemon available, their LVL and HP, and user will select from the list
            pokeSwitch();
            // computer attacks your pokemon
            opponentTurn();
        }
        // else if you are in between fights [inBattle=false]
        else if (inBattle) {
            // will output the list of pokemon available, their LVL and HP, and user will select from the list
            pokeSwitch();
            // switch [inBattle=true]
            inBattle = true;
        }
        // go back to main battle options
        startPokeBattle();
    }

    public void runAway() {
        clear();
        // TODO: go straight to game over, maybe say something cheeky
    }

    /*
     * Minor methods----------------------------------------------------------------------------------------------------
     */
    public void nextPokemon() {     // just for the current opponent's pokemon
        clear();
        boolean hasValidPokemon = false;
        for (Map.Entry<Integer, Pokemon> pokemon : activeOpponent.getPokemon().entrySet()) {
            if (!pokemon.getValue().isFainted()) {
                opponentPokemon = pokemon.getValue();
                hasValidPokemon = true;
                break;
            }
        }
        if (!hasValidPokemon) {
            System.out.printf("You have defeated %s!", activeOpponent.getName());
            nextOpponent();
        }
    }

    public void nextOpponent() {    // just for switching the opponents
        for (Map.Entry<Integer, Trainer> opponent : opponents.entrySet()) {
            boolean hasValidPokemon = false;
            for (int i = 0; i < opponent.getValue().getPokemon().size(); i++) {
                if (opponent.getValue().getPokemon().get(i).getHitPoints() > 0) {
                    activeOpponent = opponent.getValue();
                    opponentPokemon = opponent.getValue().getPokemon().get(i);
                    System.out.printf("Your next opponent is %s!", activeOpponent.getName());
                    hasValidPokemon = true;
                    break;
                }
            }
            if (hasValidPokemon) {
                return;
            }
            else {
                System.out.println("You defeated the Elite 4 and the surprise champion!");
                runAway();
            }
        }
    }

    private void pokeSwitch() {
        clear();
        System.out.println(" Option \tPokemon    \tLevel \tHP  \tisFainted");
        System.out.println(" ====== \t========== \t===== \t=== \t=========");
        for (Map.Entry<Integer, Pokemon> pokemon : player.getPokemon().entrySet()) {
            System.out.printf("   %s: \t\t%s \t %s \t%s \t%s\n"
                    , pokemon.getKey(), pokemon.getValue().getName()
                    , pokemon.getValue().getLevel(), pokemon.getValue().getHitPoints(), pokemon.getValue().isFainted());
        }
        String pokemonPrompt = prompter.prompt("Select pokemon #", "1|2|3|4", "\nThis is not a valid option!\n");
        Pokemon selectedPokemon = player.getPokemon().get(Integer.parseInt(pokemonPrompt));
        if (selectedPokemon.isFainted() || selectedPokemon.getHitPoints() <= 0) {
            System.out.println("Cannot select pokemon");
        }
        else {
            activePokemon = selectedPokemon;
            System.out.printf("You selected %s.", activePokemon.getName());
        }
    }

    private void opponentTurn() {
        int opponentAttack = opponentPokemon.attack(activePokemon);
        System.out.printf("%s uses %s\n", opponentPokemon.getName(), opponentPokemon.getAttack());
        System.out.printf("Deals %s points of damage to %s\n", opponentAttack, activePokemon.getName());
    }
}