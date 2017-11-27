package com.codecool.jlamas.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import com.codecool.jlamas.database.CityDAO;
import com.codecool.jlamas.database.GroupDAO;
import com.codecool.jlamas.models.accountdata.Group;
import com.codecool.jlamas.views.GroupTeamView;

public class GroupController {

    // decide on maximum amount of groups on one year
    private static final int MAX_GROUPS = 4;
    // decide which years beside current will be displayed
    private static final int PAST_YEARS = 1;
    private static final int NEXT_YEARS = 4;

    private GroupTeamView groupView;
    private GroupDAO groupDAO;

    public GroupController() {
        this.groupView = new GroupTeamView();
        this.groupDAO = new GroupDAO();
    }

    public Group getGroup(Integer id) {
        return this.groupDAO.getGroup(id);
    }

    public ArrayList<Group> getAllGroups() {
        return groupDAO.selectAll();
    }

    public void createGroup() {
        String name = groupView.getString("\nType name of new group: ");
        Group group = new Group(name);
        groupDAO.insert(group);
    }

    public void createGroupFromMap(Map<String, String> attrs) {
        // TODO data validation --> groupView.getString("\nType name of new group: ")
        CityDAO cityDAO = new CityDAO();
        Group group = new Group();
        group.setCity(cityDAO.get(attrs.get("city")));
        group.setYear(Integer.valueOf(attrs.get("year")));
        group.setNumber(Integer.valueOf(attrs.get("number")));

        groupDAO.insert(group);
    }

    public void editGroupFromMap(Map<String, String> attrs, Integer id) {
        // TODO data validation --> groupView.getString("\nType name of new group: ")
        // TODO GroupDAO update method is different to any other similar
        Group group = this.groupDAO.getGroup(id);
        groupDAO.update(group);
    }

    public void removeGroup(Integer groupID) {
        this.groupDAO.delete(this.groupDAO.getGroup(groupID));
    }

    public void displayGroups() {
        ArrayList<Group> groups = getAllGroups();
        groupView.printAll(groups);
    }

    public Group chooseGroup() {
        displayGroups();
        Integer record = groupView.getInt("Choose group: ");
        Integer index = record - 1;
        if (index >= getAllGroups().size()) {
            throw new IndexOutOfBoundsException();
        }

        return getAllGroups().get(index);
    }

    public void editGroup() {
        try {
            Group group = chooseGroup();
            String oldName = group.getName();
            String name = groupView.getString("New name of group: ");
            group.setName(name);
            groupDAO.update(group);

        } catch (IndexOutOfBoundsException e) {
            e.getMessage();
        }
    }

    public ArrayList<Integer> getYears() {
        ArrayList<Integer> years = new ArrayList<Integer>();

        Integer year = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = year-PAST_YEARS; i < year+NEXT_YEARS; i++) {
            years.add(i);
        }
        return years;
    }

    public ArrayList<Integer> getAvailableGroupNumbers() {
        ArrayList<Integer> groupNumbers = new ArrayList<Integer>();

        for (int i = 1; i < MAX_GROUPS+1 ; i++) {
            groupNumbers.add(i);
        }

        return groupNumbers;
    }
}
