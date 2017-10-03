package com.codecool.jlamas.views;

import com.codecool.jlamas.models.artifact.Artifact;

import java.util.ArrayList;
import java.util.Scanner;

public class ArtifactView {

    public ArtifactView() {

    }

    public void printArtifacts(ArrayList<Artifact> artifactList) {
        Integer i = 1;
        for(Artifact artifact : artifactList) {
            System.out.print(i + ". ");
            System.out.println(artifact);
            i++;
        }
    }

    public void displayAttribute() {
        String[] attributes = {"name",
                "price",
                "description"};
        printMenu(attributes);
    }

    public void printMenu(String[] options) {
        String output;

        output = "\nMENU\n";
        for (int i = 0; i < options.length; i++) {
            output += String.format("  %d) %s.\n", i+1, options[i]);
        }
        output += "  0) Exit.\n";

        System.out.println(output);
    }

    public int getMenuOption() {
        int option;
        Scanner input = new Scanner(System.in);

        System.out.print("Choose option: ");
        while (!input.hasNextInt()) {
            printErrorMessage();
            input.next();
        }

        option = input.nextInt();
        return option;
    }

    public String getString(String msg) {
        String userInput;

        System.out.println("\n" + msg + ": ");
        userInput = new Scanner(System.in).nextLine();
        return userInput;
    }

    public void printErrorMessage() {
        System.out.println("It's not a number!");
    }
}
