package com.javapoke.app;

import com.apps.util.Prompter;
import com.javapoke.Pokemon;
import com.javapoke.Trainer;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static com.apps.util.Console.blankLines;
import static org.junit.Assert.*;

public class JavaPokeAppTest {
    private Prompter prompter;
    private Trainer player = new Trainer("jorge");

    // chooseTrainer() Tests
    @Test
    public void chooseTrainer_shouldReturnBrock_whenOption3IsSelected()
    throws FileNotFoundException {
        int maxLength = 12;
        List<Trainer> trainers = new ArrayList<>(List.of(new Trainer("Ash")
                , new Trainer("Misty"), new Trainer("Brock")));
        prompter = new Prompter(new Scanner(new File("responses/response_Brock_for_chooseTrainer.txt")));

        String input = prompter.prompt("\t\t What choice would you like to choose: ", "^[1-4]$"
                , "\n\t\t This is not a valid option!\n");
        switch (Integer.parseInt(input)) {
            case 1:
                player = trainers.get(0);
                break;
            case 2:
                player = trainers.get(1);
                break;
            case 3:
                player = trainers.get(2);
                break;
            case 4:
                blankLines(1);
                String characterName = prompter.prompt("\t\t What is the name of your Trainer: "
                        , "^.{1,"+ maxLength + "}$", "\n\t\t Name must not exceed " + maxLength
                                + " characters!\n");
                player = new Trainer(characterName);
                break;
        }
        assertEquals("Brock",player.getName());
    }

    @Test
    public void chooseTrainer_shouldReturnMisty_whenOption2IsSelected()
    throws FileNotFoundException {
        int maxLength = 12;
        List<Trainer> trainers = new ArrayList<>(List.of(new Trainer("Ash")
                , new Trainer("Misty"), new Trainer("Brock")));
        prompter = new Prompter(new Scanner(new File("responses/response_Misty_for_chooseTrainer.txt")));

        String input = prompter.prompt("\t\t What choice would you like to choose: ", "^[1-4]$"
                , "\n\t\t This is not a valid option!\n");
        switch (Integer.parseInt(input)) {
            case 1:
                player.setName(trainers.get(0).getName());
                break;
            case 2:
                player.setName(trainers.get(1).getName());
                break;
            case 3:
                player.setName(trainers.get(2).getName());
                break;
            case 4:
                blankLines(1);
                String characterName = prompter.prompt("\t\t What is the name of your Trainer: "
                        , "^.{1,"+ maxLength + "}$", "\n\t\t Name must not exceed " + maxLength
                                + " characters!\n");
                player.setName(characterName);
                break;
        }
        assertEquals("Misty",player.getName());
    }

    @Test
    public void chooseTrainer_shouldReturnAsh_whenOption1IsSelected()
    throws FileNotFoundException {
        int maxLength = 12;
        List<Trainer> trainers = new ArrayList<>(List.of(new Trainer("Ash")
                , new Trainer("Misty"), new Trainer("Brock")));
        prompter = new Prompter(new Scanner(new File("responses/response_Ash_for_chooseTrainer.txt")));

        String input = prompter.prompt("\t\t What choice would you like to choose: ", "^[1-4]$"
                , "\n\t\t This is not a valid option!\n");
        switch (Integer.parseInt(input)) {
            case 1:
                player.setName(trainers.get(0).getName());
                break;
            case 2:
                player.setName(trainers.get(1).getName());
                break;
            case 3:
                player.setName(trainers.get(2).getName());
                break;
            case 4:
                blankLines(1);
                String characterName = prompter.prompt("\t\t What is the name of your Trainer: "
                        , "^.{1,"+ maxLength + "}$", "\n\t\t Name must not exceed " + maxLength
                                + " characters!\n");
                player.setName(characterName);
                break;
        }
        assertEquals("Ash",player.getName());
    }

    @Test
    public void chooseTrainer_shouldReturnInputName_whenOption4IsSelected()
    throws FileNotFoundException {
        int maxLength = 12;
        List<Trainer> trainers = new ArrayList<>(List.of(new Trainer("Ash")
                , new Trainer("Misty"), new Trainer("Brock")));
        prompter = new Prompter(new Scanner(new File("responses/response_CreateYourTrainer_for_chooseTrainer.txt")));

        String input = prompter.prompt("\t\t What choice would you like to choose: ", "^[1-4]$"
                , "\n\t\t This is not a valid option!\n");
        switch (Integer.parseInt(input)) {
            case 1:
                player.setName(trainers.get(0).getName());
                break;
            case 2:
                player.setName(trainers.get(1).getName());
                break;
            case 3:
                player.setName(trainers.get(2).getName());
                break;
            case 4:
                blankLines(1);
                String characterName = prompter.prompt("\t\t What is the name of your Trainer: "
                        , "^.{1,"+ maxLength + "}$", "\n\t\t Name must not exceed " + maxLength
                                + " characters!\n");
                player.setName(characterName);
                break;
        }
        assertEquals("Jorge",player.getName());
    }

    @Test
    public void chooseTrainer_shouldNotAccept1stInputAndOnlyAccept2ndInput_when1stInputIsInvalid()
    throws FileNotFoundException {
        int maxLength = 12;
        List<Trainer> trainers = new ArrayList<>(List.of(new Trainer("Ash")
                , new Trainer("Misty"), new Trainer("Brock")));
        prompter = new Prompter(new Scanner(new File
                ("responses/response_firstInvalidName_for_chooseTrainer.txt")));

        String input = prompter.prompt("\t\t What choice would you like to choose: ", "^[1-4]$"
                , "\n\t\t This is not a valid option!\n");
        switch (Integer.parseInt(input)) {
            case 1:
                player.setName(trainers.get(0).getName());
                break;
            case 2:
                player.setName(trainers.get(1).getName());
                break;
            case 3:
                player.setName(trainers.get(2).getName());
                break;
            case 4:
                blankLines(1);
                String characterName = prompter.prompt("\t\t What is the name of your Trainer: "
                        , "^.{1,"+ maxLength + "}$", "\n\t\t Name must not exceed " + maxLength
                                + " characters!\n");
                player.setName(characterName);
                break;
        }
        String declinedInput = "";
        assertEquals("a",player.getName());         // 1 character lower limit
        assertNotEquals(declinedInput,player.getName());
        assertTrue(declinedInput.isBlank());                // 0 character beyond lower limit
    }

    @Test
    public void chooseTrainer_shouldAcceptTheValid2ndInput_when1stInputHasMoreThan12CharactersLong()
    throws FileNotFoundException {
        int maxLength = 12;
        List<Trainer> trainers = new ArrayList<>(List.of(new Trainer("Ash")
                , new Trainer("Misty"), new Trainer("Brock")));
        prompter = new Prompter(new Scanner(new File
                ("responses/response_CreateYourTrainer_InvalidResponse_chooseTrainer.txt")));

        String input = prompter.prompt("\t\t What choice would you like to choose: ", "^[1-4]$"
                , "\n\t\t This is not a valid option!\n");
        switch (Integer.parseInt(input)) {
            case 1:
                player.setName(trainers.get(0).getName());
                break;
            case 2:
                player.setName(trainers.get(1).getName());
                break;
            case 3:
                player.setName(trainers.get(2).getName());
                break;
            case 4:
                blankLines(1);
                String characterName = prompter.prompt("\t\t What is the name of your Trainer: "
                        , "^.{1,"+ maxLength + "}$", "\n\t\t Name must not exceed " + maxLength
                                + " characters!\n");
                player.setName(characterName);
                break;
        }
        String acceptedInput = "abcdefghijkl";                  // 12 Char Upper Limit
        String declinedInput = "abcdefghijklm";                 // 13 Char to test edge case
        assertNotEquals(declinedInput,player.getName());
        assertEquals(acceptedInput,player.getName());
        assertEquals(acceptedInput.length(),12);
        assertEquals(declinedInput.length(),13);
    }

    // choosePokemon() Tests
    @Test
    public void choosePokemon_shouldAcceptAllInputOfPokemon_whenNotSelectingDuplicatesAndGivenValidInputs()
    throws FileNotFoundException {
        JavaPokeApp app = new JavaPokeApp();
        Map<Integer, Pokemon> pokemonMap = app.loadPokemonMap();
        Map<Integer, Pokemon> trainerPokemon = new HashMap<>();      // changed to map instead of list
        int maxNumOfPokemon = 4;

        prompter = new Prompter(new Scanner(new File
                ("responses/response_allValidInput_for_choosePokemon.txt")));

        for (int i = 0; i < maxNumOfPokemon; i++) {
            String pokemonPrompt = prompter.prompt("\t Input the Option # to select pokemon #" + (i + 1) + ": "
                    , "^[1-9]|10$", "\n\t\t This is not a valid option!\n");
            if (!trainerPokemon.containsValue(pokemonMap.get(Integer.parseInt(pokemonPrompt)))) {
                trainerPokemon.put(i + 1, pokemonMap.get(Integer.parseInt(pokemonPrompt)));
            } else {
                System.out.println("\n     Can not choose duplicate Pokemon for this challenge.\n");
                i--;
            }
        }
        player.setPokemon(trainerPokemon);
        assertEquals(new Pokemon("Eevee",60,145).toString()
                ,player.getPokemon().get(1).toString());
        assertEquals(new Pokemon("Blastoise",62,169).toString()
                ,player.getPokemon().get(2).toString());
        assertEquals(new Pokemon("Machamp",61,180).toString()
                ,player.getPokemon().get(3).toString());
        assertEquals(new Pokemon("Gengar",62,165).toString()
                ,player.getPokemon().get(4).toString());
    }

    @Test
    public void choosePokemon_shouldRejectDuplicatesAndAcceptValidInputs_whenGivenDuplicatesInputsAndInvalidInput()
    throws FileNotFoundException {
        JavaPokeApp app = new JavaPokeApp();
        Map<Integer, Pokemon> pokemonMap = app.loadPokemonMap();
        Map<Integer, Pokemon> trainerPokemon = new HashMap<>();      // changed to map instead of list
        int maxNumOfPokemon = 4;

        prompter = new Prompter(new Scanner(new File
                ("responses/response_duplicateInputs_for_choosePokemon.txt")));

        for (int i = 0; i < maxNumOfPokemon; i++) {
            String pokemonPrompt = prompter.prompt("\t Input the Option # to select pokemon #" + (i + 1) + ": "
                    , "^[1-9]|10$", "\n\t\t This is not a valid option!\n");
            if (!trainerPokemon.containsValue(pokemonMap.get(Integer.parseInt(pokemonPrompt)))) {
                trainerPokemon.put(i + 1, pokemonMap.get(Integer.parseInt(pokemonPrompt)));
            } else {
                System.out.println("\n     Can not choose duplicate Pokemon for this challenge.\n");
                i--;
            }
        }
        player.setPokemon(trainerPokemon);
        assertEquals(new Pokemon("Eevee",60,145).toString()
                ,player.getPokemon().get(1).toString());
        assertEquals(new Pokemon("Blastoise",62,169).toString()
                ,player.getPokemon().get(2).toString());
        assertEquals(new Pokemon("Machamp",61,180).toString()
                ,player.getPokemon().get(3).toString());
        assertEquals(new Pokemon("Gengar",62,165).toString()
                ,player.getPokemon().get(4).toString());
    }

    @Test
    public void choosePokemon_shouldRejectInvalidInputsAndAcceptNonDuplicates_whenGivenDuplicatesAndGivenValidInputs()
    throws FileNotFoundException {
        JavaPokeApp app = new JavaPokeApp();
        Map<Integer, Pokemon> trainerPokemon = new HashMap<>();      // changed to map instead of list
        int maxNumOfPokemon = 4;

        prompter = new Prompter(new Scanner(new File
                ("responses/response_allValidInput_for_choosePokemon.txt")));

        for (int i = 0; i < maxNumOfPokemon; i++) {
            String pokemonPrompt = prompter.prompt("\t Input the Option # to select pokemon #" + (i + 1) + ": "
                    , "^[1-9]|10$", "\n\t\t This is not a valid option!\n");
            if (!trainerPokemon.containsValue(app.loadPokemonMap().get(Integer.parseInt(pokemonPrompt)))) {
                trainerPokemon.put(i + 1, app.loadPokemonMap().get(Integer.parseInt(pokemonPrompt)));
            } else {
                System.out.println("\n     Can not choose duplicate Pokemon for this challenge.\n");
                i--;
            }
        }
        player.setPokemon(trainerPokemon);
        assertEquals(new Pokemon("Eevee",60,145).toString()
                ,player.getPokemon().get(1).toString());
        assertEquals(new Pokemon("Blastoise",62,169).toString()
                ,player.getPokemon().get(2).toString());
        assertEquals(new Pokemon("Machamp",61,180).toString()
                ,player.getPokemon().get(3).toString());
        assertEquals(new Pokemon("Gengar",62,165).toString()
                ,player.getPokemon().get(4).toString());
    }
}