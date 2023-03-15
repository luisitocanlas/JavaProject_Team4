package com.javapoke.app;

import com.apps.util.Prompter;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;

public class IntroductionTest {
    Prompter prompter;

    /*
     * Ask Jay how we would test to see if the method printed rules without using Mockito?
     */
    @Test
    public void welcomePrompt_shouldPrintUpperCaseY_whenGivenInputOfLowerCase() {
        List<String> option = new ArrayList<>();
        try {
            prompter = new Prompter(new Scanner(new File("responses/response_y_for_welcomePrompt.txt")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String input = prompter.prompt("\t\t\t Enter your input: "
                , "Y|N|y|n", "\n\t\t\t This is not a valid option!\n");
        option.add(input);
        assertEquals("y",option.get(0));
    }
}