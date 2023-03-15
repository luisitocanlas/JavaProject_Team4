package com.javapoke;

import com.apps.util.Prompter;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static com.apps.util.Console.*;
import static com.javapoke.Potion.*;

// TODO: move to com.javapoke.app
public class PokeBattleTest {
    // fixture
    private Prompter prompter;
    Trainer player;
    Trainer elite1;
    Trainer elite2;
    Trainer elite3;
    Trainer elite4;
    Trainer surprise;
    Pokemon activePokemon;
    Pokemon charmander;
    Pokemon squirtle;
    Pokemon bulbasaur;
    Pokemon lapras;
    Pokemon machamp;
    Pokemon gengar;
    Pokemon dragonite;
    Pokemon mewtwo;
    Map<Integer, Pokemon> pokeBelt;
    Map<Integer, Pokemon> pokeBelt2;
    Map<Integer, Trainer> opponents;
    Trainer activeOpponent;
    Pokemon opponentPokemon;

    // potion counter
    int potion = 10;
    int superPotion = 5;

    boolean inBattle = false;   // will be a check when switching pokemon

    @Before
    public void setUp() {
        prompter = new Prompter(new Scanner(System.in));

        // TODO: figure out how to do this when getting the data from the csv.file and user prompts
        // create pokemon and add the attacks
        charmander = new Pokemon("Charmander", 62, 165, "Ember");
        squirtle = new Pokemon("Squirtle", 60, 145, "Water ball");
        bulbasaur = new Pokemon("Bulbasaur", 61, 155, "Vine Whip");
        lapras = new Pokemon("Lapras", 60, 130, "Blizzard");
        machamp = new Pokemon("Machamp", 60, 90, "Body Slam");
        gengar = new Pokemon("Gengar", 60, 60, "Night Shade");
        dragonite = new Pokemon("Dragonite", 60, 91, "Hyper Beam");
        mewtwo = new Pokemon("Mewtwo", 60, 106, "Psychic");

        // TODO: figure out how to do this when getting the data from the csv.file and user prompts
        // create a pokemon storage that the trainer carries
        pokeBelt = Map.of(1,charmander, 2,squirtle, 3,bulbasaur);
        pokeBelt2 = Map.of(1,squirtle, 2,charmander, 3,bulbasaur);

        // TODO: implement the option to choose from a list of names or type in their own name
        player = new Trainer("Ash", pokeBelt);              // will be replaced by the chooserTrainer()
        elite1 = new Trainer("Lorelei", Map.of(1,lapras, 2,charmander, 3,squirtle, 4,bulbasaur));
        elite2 = new Trainer("Bruno", Map.of(1,machamp, 2,charmander, 3,squirtle, 4,bulbasaur));
        elite3 = new Trainer("Agatha", Map.of(1,gengar, 2,charmander, 3,squirtle, 4,bulbasaur));
        elite4 = new Trainer("Lance", Map.of(1,dragonite, 2,charmander, 3,squirtle, 4,bulbasaur));
        surprise = new Trainer("THE Joshua BLOCH", Map.of(1,mewtwo, 2,charmander, 3,squirtle, 4,bulbasaur));

        // TODO: implement something that tells you which is the current active pokemon
        // container for the active pokemon for the current battle
        activePokemon = player.getPokemon().get(1);

        // list of opponents to go through, data will be from csv
        opponents = Map.of(1,elite1, 2,elite2, 3,elite3, 4,elite4, 5,surprise);

        // container for active opponent, and their active pokemon, will initialize for the first opponent
        activeOpponent = elite1;
        opponentPokemon = activeOpponent.getPokemon().get(1);
    }

    @Test
    public void opponentsTrainerCheck() {
        clear();
        boolean noPokemon = true;
        for (Map.Entry<Integer, Trainer> opponent : opponents.entrySet()){
            boolean allFainted = true;
            for (Pokemon pokemon : opponent.getValue().getPokemon().values()) {
                if (!pokemon.isFainted()) {
                    allFainted = false;
                    noPokemon = false;
                    activeOpponent = opponent.getValue();
                    opponentPokemon = pokemon;
                    break;
                }
            }
            if (allFainted) {
                noPokemon = true;
                System.out.println("You defeated the Elite 4 and the surprise champion!\n");
                blankLines(1);
//                runAway();
            }
        }
        if (noPokemon) {
            System.out.println("You defeated the Elite 4 and the surprise champion!\n");
            blankLines(1);
//            runAway();
        }
    }

    @Test
    public void opponentsPokemonCheck() {
        clear();
        boolean allFainted = true;
        for (Pokemon pokemon : activeOpponent.getPokemon().values()) {
            if (!pokemon.isFainted()) {
                allFainted = false;
                opponentPokemon = pokemon;
                System.out.printf("%s selected %s.\n", activeOpponent.getName(), opponentPokemon.getName());
                pause(2_000);
                blankLines(1);
//                battle();
            }
        }
        if (allFainted) {
            System.out.printf("Congratulations on defeating %s!", activeOpponent.getName());
            pause(2_000);
            blankLines(1);
//            forThoseWhoFight();
        }
    }

    @Test
    public void prompter_test() {
//        String battlePrompt = prompter.prompt("What will you do?", "1|2|3|4", "\nThis is not a valid option!\n");
        String battlePrompt = "3";
        switch (Integer.parseInt(battlePrompt)) {
            case 1:
                System.out.println("Fight");
                break;
            case 2:
                System.out.println("Use Item");
                break;
            case 3:
                System.out.println("Switch Pokemon");
                break;
            case 4:
                System.out.println("Run away");
                break;
        }
    }

    @Test
    public void battle_choices() {
        System.out.println(" [1] Fight      [3] Switch Pokemon");
        System.out.println(" [2] Use Item   [4] Run Away");
        blankLines(2);

        System.out.println("[1] Challenge the next Elite 4");
        System.out.println("[2] Give up and continue the challenge another day");
    }

    @Test
    public void fight_attackAndReceiveDamage() {
        // show activePokemon
        System.out.println(activePokemon);
        // show activeOpponent's pokemon
        System.out.println(opponentPokemon);
        System.out.println();

        // player attacks
        int playerAttack = activePokemon.attack(opponentPokemon);
        System.out.printf("%s uses %s\n", activePokemon.getName(), activePokemon.getAttack());
        System.out.printf("Deals %s points of damage to %s\n", playerAttack, opponentPokemon.getName());

        System.out.println(opponentPokemon);
        System.out.println();

        // opponent attacks
        int opponentAttack = opponentPokemon.attack(activePokemon);
        System.out.printf("%s uses %s\n", opponentPokemon.getName(), opponentPokemon.getAttack());
        System.out.printf("Deals %s points of damage to %s\n", opponentAttack, activePokemon.getName());

        System.out.println(activePokemon);
        System.out.println();

        //end of turn
    }

    @Test
    public void switchingPokemon() {
        inBattle = true;
        bulbasaur.setFainted(true);

        clear();

        // if in a battle [inBattle = true]
        if (!inBattle) {
            // will output the list of pokemon available, their LVL and HP, and user will select from the list
            System.out.println("Choose a Pokemon.");
            pokeSwitch();
            // computer attacks your pokemon
//            opponentTurn();
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
        System.out.println(" Option     Pokemon       Level    HP    isFainted");
        System.out.println("  ======   ============    =====   ====   =========");
        for (Map.Entry<Integer, Pokemon> pokemon : player.getPokemon().entrySet()) {
            System.out.printf("%5s:      %-10s      %s      %-5s   %s\n"
                    , pokemon.getKey(), pokemon.getValue().getName()
                    , pokemon.getValue().getLevel(), pokemon.getValue().getHitPoints(), pokemon.getValue().isFainted());
        }
        String pokemonPrompt = "1";     // Select pokemon # from your
//        String pokemonPrompt = prompter.prompt("Select pokemon #", "1|2|3|4", "\nThis is not a valid option!\n");
        Pokemon selectedPokemon = player.getPokemon().get(Integer.parseInt(pokemonPrompt));
        if (selectedPokemon.isFainted() || selectedPokemon.getHitPoints() <= 0) {
            System.out.println("Cannot select pokemon");
        }
        else {
            activePokemon = selectedPokemon;
            System.out.printf("%s is selected.", activePokemon.getName());
        }
    }

    @Test
    public void useItem_promptTest() {
//        String prompt = prompter.prompt("Select [1] for Potion or [2] for Super Potion: ", "1|2",
//                "\nThis is not a valid option!\n");
        String prompt = "2";        // Select [1] for Potion or [2] for Super Potion
//        potion = 0;           // set quantity to 0
//        superPotion = 0;      // set quantity to 0

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
    }

    @Test
    public void trainerAccess_PokemonAttack() {
        System.out.println(activePokemon);
        System.out.println(player.getPokemon().get(1).getAttack());
    }

    @Test
    public void trainerAccessTest() {
        System.out.println(player.getClass().getSimpleName());
        System.out.println(player.getName());
        System.out.println(player.getPokemon());
    }
}