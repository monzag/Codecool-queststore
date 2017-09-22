package com.codecool.jlamas.models.accountdata;

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
}
