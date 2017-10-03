package com.codecool.jlamas.controllers;

import java.util.ArrayList;

import com.codecool.jlamas.database.GroupDAO;
import com.codecool.jlamas.models.accountdata.Group;
import com.codecool.jlamas.views.GroupTeamView;

public class GroupController {

    private GroupTeamView groupView;
    private GroupDAO groupDAO;

    public GroupController() {
        this.groupView = new GroupTeamView();
        this.groupDAO = new GroupDAO();
    }

    public void createGroup() {
        String name = groupView.getString("\nType name of new group: ");
        Group group = new Group(name);
        groupDAO.insertGroup(group);
    }

    public void displayGroups() {
        ArrayList<Group> groups = groupDAO.selectAll();
        groupView.printGroups(groups);
    }
}
