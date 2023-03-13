package com.javapoke;

import com.javapoke.Potion;

import com.apps.util.Console;
import com.apps.util.Prompter;

import java.util.*;
import java.util.stream.Stream;

import static com.javapoke.Potion.*;


public class PokeBattle {
    private final Prompter prompter = new Prompter(new Scanner(System.in));
    boolean inBattle = false;       // will be a check when switching pokemon
    List<Pokemon> pokeStorage;      // will contain the pokemon selected by the user by the choosePokemon()

    /*
     * Code within the dashes are used for method functionality, may keep some of them
     */
    //------------------------------------------------------------------------------------------------------------------
    // Potion Counter
    int potion = 10;
    int superPotion = 5;

    // TODO: figure out how to do this when getting the data from the csv.file and user prompts
    // create pokemon and add the attacks
    Pokemon charmander = new Pokemon("Charmander", 62, 165, "Ember");
    Pokemon squirtle = new Pokemon("Squirtle", 60, 145, "Water ball");
    Pokemon lapras = new Pokemon("Lapras", 60, 130, "Blizzard");
    Pokemon machamp = new Pokemon("Machamp", 60, 90, "Body Slam");
    Pokemon gengar = new Pokemon("Gengar", 60, 60, "Night Shade");
    Pokemon dragonite = new Pokemon("Dragonite", 60, 91, "Hyper Beam");
    Pokemon mewtwo = new Pokemon("Mewtwo", 60, 106, "Psychic");

    // TODO: figure out how to do this when getting the data from the csv.file and user prompts
    // create a pokemon storage that the trainer carries
    List<Pokemon> pokeBelt = new ArrayList<>(List.of(charmander, squirtle));

    // TODO: implement the option to choose from a list of names or type in their own name
    Trainer trainer = new Trainer("Ash", pokeBelt);              // will be replaced by the chooserTrainer()
    Trainer elite1 = new Trainer("Lorelei", new ArrayList<>(List.of(lapras)));
    Trainer elite2 = new Trainer("Bruno", new ArrayList<>(List.of(machamp)));
    Trainer elite3 = new Trainer("Agatha", new ArrayList<>(List.of(gengar)));
    Trainer elite4 = new Trainer("Lance", new ArrayList<>(List.of(dragonite)));
    Trainer surprise = new Trainer("THE Joshua BLOCH", new ArrayList<>(List.of(mewtwo)));

    // TODO: implement something that tells you which is the current active pokemon
    // container for the active pokemon for the current battle
    Pokemon activePokemon = trainer.getPokemon().get(0);

    // list of opponents to go through
    List<Trainer> opponents = new ArrayList<>(List.of(elite1, elite2, elite3, elite4, surprise));

    // container for active opponent
    Trainer activeOpponent = elite1;
    //------------------------------------------------------------------------------------------------------------------

    /*
     * These methods are only for the user. The computer will only use fight(), implementation of the
     * other methods will be done for the future update.
     */
    // methods
    public void startPokeBattle() {
        // pick a pokemon from your list, maybe use switchPokemon method here
        // set activeOpponent to the first in the list
        // show options: fight, items, switch
    }

    public void fight() {
        // will look through the map or list of attacks from the specific pokemon and use that attack
        // computer attacks your pokemon
    }

    public void useItem() {
        Console.clear();
        boolean validInput = false;
        while (!validInput) {
            // ask the player which potion to use
            Stream.of(Potion.values()).forEach(System.out::println);
            String prompt = prompter.prompt("Select your Potion: ", "1|2", "\nThis is not a valid option!\n");

            if (potion >= 1 && Integer.parseInt(prompt) == 1) {
                potion -= 1;
                activePokemon.setHitPoints(activePokemon.getHitPoints() + POTION.getValue());
                validInput = true;
            }
            else if (superPotion >= 1 && Integer.parseInt(prompt) == 2) {
                superPotion -= 1;
                activePokemon.setHitPoints(activePokemon.getHitPoints() + SUPER_POTION.getValue());
                validInput = true;
            }
            else if (potion == 0 && superPotion == 0) {
                System.out.println("You have run out of items!");
                validInput = true;
            }
        }

        // computer attacks your pokemon
    }

    public void switchPokemon() {
        Console.clear();

        // if in a battle [inBattle = true]
        if (!inBattle) {
            // will output the list of pokemon available, their LVL and HP, and user will select from the list
            for (Pokemon pokemon : pokeBelt) {
                System.out.println(pokemon);
            }
            // cannot select pokemon with isFainted=true
            // set activePokeman
            // computer attacks your pokemon
        }


        // else if you are in between fights [inBattle=false]
        else {
            // will output the list of pokemon available, their LVL and HP, and user will select from the list
            for (Pokemon pokemon : pokeBelt) {
                System.out.println(pokemon);
            }
            // cannot select pokemon with isFainted=true
            // set activePokeman
            // go to the next battle
            // switch [inBattle=true]
        }
    }

    public void runAway() {
        // go straight to game over, maybe say something cheeky
    }

    public int attackDamage() {
        int pain = 0;
        Random random = new Random();
        pain = random.nextInt(30) + 1;      // damage will be between 1~30, attack modifier will be based on
        return pain;
    }
}