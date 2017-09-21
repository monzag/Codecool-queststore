package com.codecool.jlamas.views;

import java.util.ArrayList;

import com.codecool.jlamas.models.account.Student;

public class StudentView extends CodecoolerView {

    public StudentView() {
        super();
    }

    public void displayAll(ArrayList<Student> students) {
        System.out.println("\nUsers:");
        Integer number = 1;
        for (Student student : students) {
            System.out.println(number + ". " + student);
            number++;
        }
    }
}