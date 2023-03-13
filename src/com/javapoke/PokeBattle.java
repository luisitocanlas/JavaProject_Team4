package com.javapoke;

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
    Map<Integer, Pokemon> pokeBelt = Map.of(1, charmander, 2, squirtle);

    // TODO: implement the option to choose from a list of names or type in their own name
    Trainer trainer = new Trainer("Ash", pokeBelt);              // will be replaced by the chooserTrainer()
    Trainer elite1 = new Trainer("Lorelei", Map.of(1, lapras));
    Trainer elite2 = new Trainer("Bruno", Map.of(1,machamp));
    Trainer elite3 = new Trainer("Agatha", Map.of(1,gengar));
    Trainer elite4 = new Trainer("Lance", Map.of(1,dragonite));
    Trainer surprise = new Trainer("THE Joshua BLOCH", Map.of(1,mewtwo));

    // TODO: implement something that tells you which is the current active pokemon
    // container for the active pokemon for the current battle
    Pokemon activePokemon = trainer.getPokemon().get(0);

    // list of opponents to go through
    Map<Integer, Trainer> opponents = Map.of(1,elite1, 2,elite2, 3,elite3, 4,elite4, 5,surprise);

    // container for active opponent, maybe change this to the opponent's pokemon instead of the trainer
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
        // show activePokemon
        System.out.println(activePokemon);
        // show activeOpponent's pokemon
        System.out.println(activeOpponent.getPokemon().get(1));
        System.out.println();

        // player attacks
        int damage = attackDamage();
        System.out.printf("%s uses %s\n", activePokemon.getName(), activePokemon.getAttack());
        System.out.printf("Deals %s points of damage to %s\n", damage,
                activeOpponent.getPokemon().get(1).getName());
        activeOpponent.getPokemon().get(1).setHitPoints(activeOpponent.getPokemon().get(1).getHitPoints()
                - damage);
        System.out.println(activeOpponent.getPokemon().get(1));
        System.out.println();

        // opponent attacks
        int damage2 = attackDamage();
        System.out.printf("%s uses %s\n", activeOpponent.getPokemon().get(1).getName(),
                activeOpponent.getPokemon().get(1).getAttack());
        System.out.printf("Deals %s points of damage to %s\n", damage2, activePokemon.getName());
        activePokemon.setHitPoints(activePokemon.getHitPoints() - damage2);
        System.out.println(activePokemon);
        System.out.println();

        // TODO: maybe do a check here if pokemon isFainted and trigger the either switch pokemon or next opponent
        //end of turn
    }

    public void useItem() {
        Console.clear();
        // TODO: make sure when using potions, HP doesn't go past the max HP.
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

        // go back to main battle options
    }

    public void switchPokemon() {
        Console.clear();

        // if in a battle [inBattle = true]
        if (!inBattle) {
            // will output the list of pokemon available, their LVL and HP, and user will select from the list
            pokeSwitch();

            // computer attacks your pokemon
        }

        // else if you are in between fights [inBattle=false]
        else if (inBattle) {
            // will output the list of pokemon available, their LVL and HP, and user will select from the list
            pokeSwitch();

            // switch [inBattle=true]
            inBattle = true;
            // go to the next battle, maybe call start battle here?
        }
    }

    private void pokeSwitch() {
        System.out.println(" Option \tPokemon \tLevel \tHP");
        System.out.println(" ====== \t======= \t===== \t===");
        for (Map.Entry<Integer, Pokemon> pokemon : pokeBelt.entrySet()) {
            System.out.printf("   %s: \t\t%s \t %s \t%s\n"
                    , pokemon.getKey(), pokemon.getValue().getName()
                    , pokemon.getValue().getLevel(), pokemon.getValue().getHitPoints());
        }
        String pokemonPrompt = prompter.prompt("Select pokemon #", "1|2|3|4", "\nThis is not a valid option!\n");
        if (trainer.getPokemon().get(Integer.parseInt(pokemonPrompt)).isFainted() ||
                trainer.getPokemon().get(Integer.parseInt(pokemonPrompt)).getHitPoints() <= 0) {
            System.out.println("Cannot select pokemon");
        }
        else {
            activePokemon = trainer.getPokemon().get(Integer.parseInt(pokemonPrompt));
            System.out.printf("%s is selected.", activePokemon.getName());
        }
    }

    public void runAway() {
        // go straight to game over, maybe say something cheeky
    }

    private int attackDamage() {
        int pain = 0;
        Random random = new Random();
        pain = random.nextInt(30) + 1;      // damage will be between 1~30, attack modifier will be for future code
        return pain;
    }
}