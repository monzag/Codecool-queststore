package com.codecool.jlamas.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import com.codecool.jlamas.database.CityDAO;
import com.codecool.jlamas.database.GroupDAO;
import com.codecool.jlamas.exceptions.InvalidGroupDataException;
import com.codecool.jlamas.models.accountdata.Group;


public class GroupController {

    // decide on maximum amount of groups on one year
    private static final int MAX_GROUPS = 4;
    // decide which years beside current will be displayed
    private static final int PAST_YEARS = 1;
    private static final int NEXT_YEARS = 4;

    private GroupDAO groupDAO;
    private CityDAO cityDAO;

    public GroupController() {
        this.groupDAO = new GroupDAO();
        this.cityDAO = new CityDAO();
    }

    public Group getGroup(Integer id) {
        return this.groupDAO.getGroup(id);
    }

    public ArrayList<Group> getAllGroups() {
        return groupDAO.getAll();
    }

    public void createGroupFromMap(Map<String, String> attrs) throws InvalidGroupDataException {
        Group group = new Group();
        group.setCity(this.cityDAO.get(attrs.get("city")));
        group.setYear(Integer.valueOf(attrs.get("year")));
        group.setNumber(Integer.valueOf(attrs.get("number")));

        if (this.isGroupUnique(group)) {
            groupDAO.insert(group);
        }
        else {
            throw new InvalidGroupDataException();
        }
    }

    public void editGroupFromMap(Map<String, String> attrs, Integer id) throws InvalidGroupDataException {
        Group group = this.groupDAO.getGroup(id);
        group.setCity(this.cityDAO.get(attrs.get("city")));
        group.setYear(Integer.valueOf(attrs.get("year")));
        group.setNumber(Integer.valueOf(attrs.get("number")));

        if (this.isGroupUnique(group)) {
            groupDAO.update(group);
        }
        else {
            throw new InvalidGroupDataException();
        }
    }

    public void removeGroup(Integer groupID) {
        this.groupDAO.delete(this.groupDAO.getGroup(groupID));
    }

    public boolean isGroupUnique(Group newGroup) {
        for (Group group : this.getAllGroups()) {
            if (group.equals(newGroup)) {
                return false;
            }
        }
        return true;
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
