package views;

import java.util.Scanner;


public class CodecoolerView {

    public static String getString(String keyword) {
        System.out.println("Provide " + keyword + ": ");
        String userInput = System.console().readLine();

        return userInput;

    }

    public static void reportResult(boolean state) {
        if (state) {
            System.out.println("Sucess!");
        } else {
            System.out.println("Failed!");
        }

    }


}
