package com.javapoke.app;

import com.javapoke.Trainer;

import java.util.Scanner;

class JavaPokeApp {
    private Trainer trainer;
    private final Scanner scanner = new Scanner(System.in);

    public void beginChallenge() {
        welcome();
        chooseTrainer();
        choosePokemon();
        startGame();
        gameOver();
    }

    private void welcome() {
        boolean validInput = false;

        while(!validInput) {
            System.out.println("W E L C O M E  T O  J A V A  P O K E!!!!");
            System.out.print("Would you like to see the rule before starting our game? " +
                    "Enter [Y]es or [N]o : ");
            String ruleInput = scanner.nextLine().trim().toUpperCase();
            if (ruleInput)

        }
    }

    private void gameOver() {
    }

    private void startGame() {
    }

    private void choosePokemon() {
    }

    private void chooseTrainer() {
    }
}