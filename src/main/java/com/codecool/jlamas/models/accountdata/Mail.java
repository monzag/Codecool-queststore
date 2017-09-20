package com.codecool.jlamas.models.accountdata;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Mail extends AccountData {
	private static final String MAILPATTERN =
		"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static Pattern pattern;
    private static Matcher matcher;

    public Mail() {
    }

    public Mail(String value) {
        this.value = value;
    }

    public static boolean isValid(final String value) {
        pattern = Pattern.compile(MAILPATTERN);
		matcher = pattern.matcher(value);
        boolean isUnique = isUnique(value);


        if (isUnique && matcher.matches()){
            return true;
        } else {
            return false;
        }
    }
}
