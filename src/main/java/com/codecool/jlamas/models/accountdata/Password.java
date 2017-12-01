package com.codecool.jlamas.models.accountdata;

import java.util.Random;

public class Password extends AccountData {
    private static final Integer PASSMAXLEN = 50;
    private static final Integer PASSMINLEN = 7;

    public Password(){
    }

    public Password(String value) {
        this.value = value;
    }

    public static boolean isValid(String value){
        boolean isLengthValid = isLengthValid(value, PASSMINLEN, PASSMAXLEN);

        return isLengthValid;
    }

    public static Password generate() {
        String alphabet= "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        String value = "";
        while (value.length() < 8) {
            char sign = alphabet.charAt(random.nextInt(36));
            value += sign;
        }

        return new Password(value);
    }
}
