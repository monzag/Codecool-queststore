package com.codecool.jlamas.controllers;

import com.codecool.jlamas.exceptions.InvalidUserDataException;

import java.util.ArrayList;
import java.util.Map;

public interface Controller<T> {

    T get(String login);

    ArrayList<T> getAll();

    void remove(String login);

    void createFromMap(Map<String, String> attrs) throws InvalidUserDataException;

    void editFromMap(Map<String, String> attrs, String login) throws InvalidUserDataException;
}
