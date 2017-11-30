package com.codecool.jlamas.controllers;

import com.codecool.jlamas.database.UserDAO;
import com.codecool.jlamas.exceptions.NotMatchingPasswordException;
import com.codecool.jlamas.models.accountdata.Password;

import java.util.Map;

public class UserController {

    private UserDAO userDAO;

    public UserController() {
        this.userDAO = new UserDAO();
    }

    public void editPassword(Map<String, String> inputs, String login) throws NotMatchingPasswordException {
        String oldPassword = inputs.get("old_password");
        Password password = userDAO.getPassword(login);

        if (password.getValue().equals(oldPassword)) {
            String newPassword = inputs.get("new_password");
            userDAO.changePassword(new Password(newPassword));

        } else {
            throw new NotMatchingPasswordException();
        }
    }
}
