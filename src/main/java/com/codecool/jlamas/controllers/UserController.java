package com.codecool.jlamas.controllers;

import com.codecool.jlamas.database.UserDAO;
import com.codecool.jlamas.exceptions.InvalidRepeatedPassword;
import com.codecool.jlamas.exceptions.NotMatchingPasswordException;
import com.codecool.jlamas.models.accountdata.Password;

import java.util.Map;

public class UserController {

    private UserDAO userDAO;

    public UserController() {
        this.userDAO = new UserDAO();
    }

    public void editPassword(Map<String, String> inputs, String login) throws NotMatchingPasswordException, InvalidRepeatedPassword {
        String oldPassword = inputs.get("old_password");
        Password password = userDAO.getPassword(login);

        if (password.getValue().equals(oldPassword)) {
            String newPassword = inputs.get("new_password");
            String repeatedNewPassword = inputs.get("repeated_new_password");
            if (newPassword.equals(repeatedNewPassword)) {
                userDAO.changePassword(new Password(newPassword), login);
            } else {
                throw new InvalidRepeatedPassword();
            }
        } else {
            throw new NotMatchingPasswordException();
        }
    }
}
