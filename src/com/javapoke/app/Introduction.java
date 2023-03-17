package com.javapoke.app;

import static com.apps.util.Console.*;

import com.apps.util.Prompter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class Introduction {

    private static String rulesOption;
    private static String introBanner;
    private static String rules;

    private final Prompter prompter;

    Introduction(Prompter prompter) {
        this.prompter = prompter;
    }

    /**
     * This method will showcase a welcome banner and afterwards will prompt
     * the user if they would like to be showcased the rules of the program.
     * If the user decides to be showcased the rules they will be given another
     * prompt to continue forward in the program.
     */
    public void startUp() {
        welcomeMessage();
        welcomePrompt();
    }

    private void welcomePrompt() {
        blankLines(1);
        clear();
        System.out.println(rulesOption);
        String input = prompter.prompt("\t\t\t Enter your input: "
                , "Y|N|y|n", "\n\t\t\t This is not a valid option!\n");
        if ("Y".equalsIgnoreCase(input)) {
            showRules();
        }
    }

    private void welcomeMessage() {
        System.out.println(introBanner);
        pause(5_000);
    }

    private void showRules() {
        clear();
        System.out.println(rules);
        prompter.prompt("\n\t\t\t\t Enter [Y]es to Continue: ", "Y|y",
                "\n\t\t\t\t This is not a valid option!\n");
    }

    static {
        try {
            rules = Files.readString(Path.of("images/rules.txt"));
            rulesOption = Files.readString(Path.of("images/rules_option.txt"));
            introBanner = Files.readString(Path.of("images/intro_banner.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}