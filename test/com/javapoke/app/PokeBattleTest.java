package com.javapoke.app;

import com.apps.util.Prompter;
import com.javapoke.EliteTrainer;
import com.javapoke.Pokemon;
import com.javapoke.Trainer;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.*;

import static com.apps.util.Console.blankLines;
import static com.apps.util.Console.pause;
import static com.javapoke.Potion.*;
import static org.junit.Assert.*;

public class PokeBattleTest {
    // fixture
    Prompter prompter;
    private Trainer trainer = new Trainer("Ash");
    private EliteTrainer lorelei = new EliteTrainer().loadLorelei();
    private EliteTrainer bruno = new EliteTrainer().loadBruno();
    private Map<Integer, Trainer> opponents;

    @Test
    public void attack_shouldReturnAttackDamage() {
        Pokemon activePokemon = new Pokemon("Snorlax",60,180,"Body Slam");
        Pokemon opponentPokemon = lorelei.getPokemon().get(1);

        System.out.println("HP: " + opponentPokemon.getHitPoints());
        int beforeAttack = opponentPokemon.getHitPoints();
        int attack = activePokemon.attack(opponentPokemon);
        System.out.printf("%s used %s\n", activePokemon.getName(), activePokemon.getAttack());
        System.out.printf("Deals %s points of damage to %s\n", attack, opponentPokemon.getName());
        System.out.println("HP: " + opponentPokemon.getHitPoints());
        int afterAttack = opponentPokemon.getHitPoints();

        assertEquals((beforeAttack - afterAttack) ,attack);
    }

    @Test
    public void opponentSwitch_shouldSwitchToNextOpponent_whenAllFainted() {
        opponents = new TreeMap<>(Map.of(1, lorelei, 2, bruno));

        Trainer activeOpponent = lorelei;
        Pokemon opponentPokemon = null;

        lorelei.getPokemon().get(1).setFainted(true);
        lorelei.getPokemon().get(2).setFainted(true);
        lorelei.getPokemon().get(3).setFainted(true);
        lorelei.getPokemon().get(4).setFainted(true);

        boolean allFainted = true;
        for (Pokemon pokemon : activeOpponent.getPokemon().values()) {
            if (!pokemon.isFainted()) {
                allFainted = false;
                opponentPokemon = pokemon;
                break;
            }
        }
        if (allFainted) {
            System.out.printf("Congratulations on defeating %s!", activeOpponent.getName());
        }
        for (Map.Entry<Integer, Trainer> opponent : opponents.entrySet()) {
            // check each elite 4 if they have pokemon alive
            for (Pokemon pokemon : opponent.getValue().getPokemon().values()) {
                if (!pokemon.isFainted()) {
                    allFainted = false;
                    activeOpponent = opponent.getValue();
                    opponentPokemon = activeOpponent.getPokemon().get(1);
                    break;
                }
            }
            if (!allFainted) {
                System.out.println("You defeated all opponents!");
                break;
            }
        }

        assertSame(bruno, activeOpponent);
        assertSame(bruno.getPokemon().get(1), opponentPokemon);
    }

    @Test
    public void opponentPokemon_shouldSwitchPokemon_whenFainted() {
        Pokemon opponentPokemon = null;

        lorelei.getPokemon().get(1).setFainted(true);
        lorelei.getPokemon().get(2).setFainted(true);

        for (Pokemon pokemon : lorelei.getPokemon().values()) {
            if (!pokemon.isFainted()) {
                opponentPokemon = pokemon;
                break;
            }
        }

        assertSame(lorelei.getPokemon().get(3), opponentPokemon);
    }

    @Test
    public void switchPokemon_shouldSwitchPokemon() throws FileNotFoundException {
        Pokemon activePokemon = null;
        JavaPokeApp app = new JavaPokeApp();
        Map<Integer, Pokemon> pokemonMap = app.loadPokemonMap();
        Map<Integer, Pokemon> trainerPokemon = new HashMap<>();
        int maxNumOfPokemon = 4;
        for (int i = 0; i < maxNumOfPokemon; i++) {
            trainerPokemon.put(i + 1, pokemonMap.get(i + 1));
        }
        trainer.setPokemon(trainerPokemon);

        System.out.println("  Option     Pokemon       Level    HP    isFainted");
        System.out.println("  ======   ============    =====   ====   =========");
        for (Map.Entry<Integer, Pokemon> pokemon : trainer.getPokemon().entrySet()) {
            System.out.printf("%5s:      %-10s      %s      %-5s   %s\n",
                    pokemon.getKey(), pokemon.getValue().getName(),
                    pokemon.getValue().getLevel(), pokemon.getValue().getHitPoints(),
                    pokemon.getValue().isFainted());
        }

        prompter = new Prompter(new Scanner(new File("responses/response_selectingPokemon.txt")));

        for (int i = 0; i < trainer.getPokemon().size(); i++) {
            String pokemonPrompt = prompter.prompt("Select pokemon #: ", "1|2|3|4", "\nThis is not a valid option!\n");
            Pokemon selectedPokemon = trainer.getPokemon().get(Integer.parseInt(pokemonPrompt));
            if (!selectedPokemon.isFainted()) {
                activePokemon = selectedPokemon;
                break;
            } else {
                i--;
                System.out.println("Cannot select pokemon\n");
                pause(2_000);
                blankLines(1);
            }
        }

        assertEquals(trainer.getPokemon().get(2), activePokemon);
    }

    @Test
    public void fight_shouldReturnRunAway() throws FileNotFoundException {
        String output = null;
        prompter = new Prompter(new Scanner(new File("responses/response_runAway.txt")));

        String battlePrompt = prompter.prompt("\t What will you do? ", "1|2|3|4", "\n\t This is not a valid option!\n");
        switch (Integer.parseInt(battlePrompt)) {
            case 1:
                output = "Pokemon attacks!";
                break;
            case 2:
                output = "Use an item!";
                break;
            case 3:
                output = "Switch Pokemon!";
                break;
            case 4:
                output = "Run Away!";
                break;
        }

        assertSame("Run Away!", output);
    }

    @Test
    public void fight_shouldReturnSwitchPokemon() throws FileNotFoundException {
        String output = null;
        prompter = new Prompter(new Scanner(new File("responses/response_switchPokemon.txt")));

        String battlePrompt = prompter.prompt("\t What will you do? ", "1|2|3|4", "\n\t This is not a valid option!\n");
        switch (Integer.parseInt(battlePrompt)) {
            case 1:
                output = "Pokemon attacks!";
                break;
            case 2:
                output = "Use an item!";
                break;
            case 3:
                output = "Switch Pokemon!";
                break;
            case 4:
                output = "Run Away!";
                break;
        }

        assertSame("Switch Pokemon!", output);
    }

    @Test
    public void fight_shouldReturnUseItem() throws FileNotFoundException {
        String output = null;
        prompter = new Prompter(new Scanner(new File("responses/response_useItem.txt")));

        String battlePrompt = prompter.prompt("\t What will you do? ", "1|2|3|4", "\n\t This is not a valid option!\n");
        switch (Integer.parseInt(battlePrompt)) {
            case 1:
                output = "Pokemon attacks!";
                break;
            case 2:
                output = "Use an item!";
                break;
            case 3:
                output = "Switch Pokemon!";
                break;
            case 4:
                output = "Run Away!";
                break;
        }

        assertSame("Use an item!", output);
    }

    @Test
    public void fight_shouldReturnAttack() throws FileNotFoundException {
        String output = null;
        prompter = new Prompter(new Scanner(new File("responses/response_fight.txt")));

        String battlePrompt = prompter.prompt("\t What will you do? ", "1|2|3|4", "\n\t This is not a valid option!\n");
        switch (Integer.parseInt(battlePrompt)) {
            case 1:
                output = "Pokemon attacks!";
                break;
            case 2:
                output = "Use an item!";
                break;
            case 3:
                output = "Switch Pokemon!";
                break;
            case 4:
                output = "Run Away!";
                break;
        }

        assertSame("Pokemon attacks!", output);
    }

    @Test
    public void useItem_shouldReturnPotionValue_whenUsingSuperPotion() throws FileNotFoundException {
        int healValue = 0;

        prompter = new Prompter(new Scanner(new File("responses/response_selectingSuperPotion.txt")));

        String prompt = prompter.prompt("Pick your poison: ", "1|2|3", "\nThis is not a valid option!\n");
        if (Integer.parseInt(prompt) == 1) {
            healValue = POTION.getValue();
        } else if (Integer.parseInt(prompt) == 2) {
            healValue = SUPER_POTION.getValue();
        }

        assertEquals(100, healValue);
    }

    @Test
    public void useItem_shouldReturnPotionValue_whenUsingPotion() throws FileNotFoundException {
        int healValue = 0;

        prompter = new Prompter(new Scanner(new File("responses/response_selectingPotion.txt")));

        String prompt = prompter.prompt("Pick your poison: ", "1|2|3", "\nThis is not a valid option!\n");
        if (Integer.parseInt(prompt) == 1) {
            healValue = POTION.getValue();
        } else if (Integer.parseInt(prompt) == 2) {
            healValue = SUPER_POTION.getValue();
        }

        assertEquals(50, healValue);
    }
}