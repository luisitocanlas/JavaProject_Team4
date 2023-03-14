package com.javapoke.app;

import com.apps.util.Prompter;
import com.javapoke.Trainer;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.apps.util.Console.blankLines;
import static org.junit.Assert.*;

public class JavaPokeAppTest {
    private Prompter prompter;
    private Trainer player;

    @Test
    public void chooseTrainer_shouldReturnBrock_whenOption3IsSelected() throws FileNotFoundException {
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
    public void chooseTrainer_shouldReturnMisty_whenOption2IsSelected() throws FileNotFoundException {
        int maxLength = 12;
        List<Trainer> trainers = new ArrayList<>(List.of(new Trainer("Ash")
                , new Trainer("Misty"), new Trainer("Brock")));
        prompter = new Prompter(new Scanner(new File("responses/response_Misty_for_chooseTrainer.txt")));

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
        assertEquals("Misty",player.getName());
    }

    @Test
    public void chooseTrainer_shouldReturnAsh_whenOption1IsSelected() throws FileNotFoundException {
        int maxLength = 12;
        List<Trainer> trainers = new ArrayList<>(List.of(new Trainer("Ash")
                , new Trainer("Misty"), new Trainer("Brock")));
        prompter = new Prompter(new Scanner(new File("responses/response_Ash_for_chooseTrainer.txt")));

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
        assertEquals("Ash",player.getName());
    }

    @Test
    public void chooseTrainer_shouldReturnInputName_whenOption4IsSelected() throws FileNotFoundException {
        int maxLength = 12;
        List<Trainer> trainers = new ArrayList<>(List.of(new Trainer("Ash")
                , new Trainer("Misty"), new Trainer("Brock")));
        prompter = new Prompter(new Scanner(new File("responses/response_CreateYourTrainer_for_chooseTrainer.txt")));

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
        assertEquals("Jorge",player.getName());
    }
}