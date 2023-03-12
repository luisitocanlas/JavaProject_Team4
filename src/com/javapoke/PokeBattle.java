package com.javapoke;

import java.util.List;
import java.util.Map;

public class PokeBattle {
    boolean inBattle = false;       // will be a check when switching pokemon
    List<Pokemon> pokeStorage;      // will contain the pokemon selected by the user by the choosePokemon()
    List<Potion> myPotions;         // will contain predetermined potion types and quantity

    // TODO: implement the option to choose from a list of names or type in their own name
    Trainer user = new Trainer("Ash");
    Trainer elite1 = new Trainer("Lorelei");
    Trainer elite2 = new Trainer("Bruno");
    Trainer elite3 = new Trainer("Agatha");
    Trainer elite4 = new Trainer("Lance");
    Trainer surprise = new Trainer("THE Joshua BLOCH");


    /*
     * These methods are only for the user. The computer will only use fight(), implementation of the
     * other methods will be done for the future update.
     */
    // methods
    public void startPokeBattle() {
        // pick a pokemon from your list, maybe use switchPokemon method here
        // show options: fight, items, switch
    }

    public void fight() {
        // will look through the map or list of attacks from the specific pokemon and use that attack
        // computer attacks your pokemon
    }

    public void useItem() {
        // will look through the list of potions to use


        // computer attacks your pokemon
    }

    public void switchPokemon() {
        // if in a battle [inBattle = true]
            // will output the list of pokemon available, their LVL and HP, and user will select from the list
            // cannot select pokemon with isFainted=true
            // computer attacks your pokemon

        // else if you are in between fights [inBattle=false]
            // will output the list of pokemon available, their LVL and HP, and user will select from the list
            // cannot select pokemon with isFainted=true
            // go to the next battle
            // switch [inBattle=true]
    }

    public void runAway() {
        // go straight to game over, maybe say something cheeky
    }

}
