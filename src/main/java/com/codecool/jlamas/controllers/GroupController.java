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
        ArrayList<Group> groups = getGroups();
        groupView.printAll(groups);
    }

    public ArrayList<Group> getGroups() {
        return groupDAO.selectAll();
    }
    
    public Group chooseGroup() {
        displayGroups();
        Integer record = groupView.getInt("Choose group: ");
        Integer index = record - 1;
        if (index >= getGroups().size()) {
            throw new IndexOutOfBoundsException();
        }

        return getGroups().get(index);
    }

    public void editGroup() {
        try {
            Group group = chooseGroup();
            String oldName = group.getName();
            String name = groupView.getString("New name of group: ");
            group.setName(name);
            groupDAO.update(group, oldName);

        } catch (IndexOutOfBoundsException e) {
            e.getMessage();
        }
    }
}
