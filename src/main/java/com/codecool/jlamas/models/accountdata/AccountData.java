package com.codecool.jlamas.models.accountdata;

import com.codecool.jlamas.database.AdminDAO;
import com.codecool.jlamas.database.MentorDAO;
import com.codecool.jlamas.database.StudentDAO;


public abstract class AccountData {
    String value;

    public AccountData() {
    }

    public AccountData(String value) {
        this.value = value;
    }

    protected static  boolean isLengthValid(String value, Integer minLen, Integer maxLen) {
        if (value.length() > minLen && value.length() < maxLen ) {
            return true;

        }else {
            return false;
        }
    }

    protected static boolean isUnique(String value){
        StudentDAO studentData = new StudentDAO();
        MentorDAO mentorData = new MentorDAO();
        AdminDAO adminData = new AdminDAO();

        if (adminData.load(value) != null){
            return false;

        }else if (mentorData.load(value) != null){
            return false;

        }else if (studentData.load(value) != null){
            return false;

        } else {
            return true;
        }
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
