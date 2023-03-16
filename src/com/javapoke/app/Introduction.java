package com.javapoke.app;

import static com.apps.util.Console.*;
import com.apps.util.Prompter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class Introduction {
    private final Prompter prompter;

    Introduction(Prompter prompter) {
        this.prompter = prompter;
    }

    /*
     * This method will call to welcome the user to the game and prompt the user if they would like
     * to read the rules or not before starting the game. If the player inputs "Y/y" they
     * will be shown the rules(). If the player inputs "N/n" the method will be complete
     * and continue in com.javapoke.app.JavaPokeApp beginChallenge()
     */
    public void startUp() {
        welcomeMessage();
        welcomePrompt();
    }

    private void welcomePrompt() {
        blankLines(1);
        clear();
        String rulesOption = "images/rules_option.txt";
        try {
            Files.readAllLines(Path.of(rulesOption)).forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String input = prompter.prompt("\t\t\t Enter your input: "
                , "Y|N|y|n", "\n\t\t\t This is not a valid option!\n");
        if ("Y".equalsIgnoreCase(input)) {
            showRules();
        }
    }

    private void welcomeMessage() {
        String introBanner = "images/intro_banner.txt";
        try {
            Files.readAllLines(Path.of(introBanner)).forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
        pause(5_000); // 5 secs
    }

    private void showRules() {
        clear();
        String rules = "images/rules.txt";
        try {
            Files.readAllLines(Path.of(rules)).forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
        pause(25_000); // 25 secs should be enough for the reader to read all rules. Ask lui
    }
}