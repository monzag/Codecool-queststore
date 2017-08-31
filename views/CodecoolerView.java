package views;

import java.util.Scanner;


public class CodecoolerView {

    public static String getString(String keyword) {
        Scanner input = new Scanner(System.in);
        String userInput;

        System.out.println("Provide " + keyword + ": ");
        userInput = input.nextLine();
        input.close();

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
