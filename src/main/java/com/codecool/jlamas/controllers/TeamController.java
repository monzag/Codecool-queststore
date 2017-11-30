package com.codecool.jlamas.controllers;

import com.codecool.jlamas.database.TeamDAO;
import com.codecool.jlamas.models.accountdata.Team;

import java.util.ArrayList;
import java.util.Map;

public class TeamController implements Controller<Team> {

    private TeamDAO teamDao;

    public TeamController() {
        this.teamDao = new TeamDAO();
    }

    public Team get(String id) {
        return teamDao.get(Integer.valueOf(id));
    }

    public ArrayList<Team> getAll() {
        return teamDao.getAll();
    };

    public void remove(String id) {
        teamDao.delete(Integer.valueOf(id));
    };

    public void createFromMap(Map<String, String> attrs) {
        String name = attrs.get("name");

        if (isNewTeamDataUnique(name)) {
            Team team = new Team(null, name);
            this.teamDao.insert(team);
        }
    };

    public void editFromMap(Map<String, String> attrs, String id) throws Exception{
        Team team = teamDao.get(Integer.valueOf(id));

        team.setName(attrs.get("name"));
        this.teamDao.update(team);
    }

    private boolean isNewTeamDataUnique(String name) {
        ArrayList<Team> teams = this.teamDao.getAll();

        for (Team team : teams) {
            if (team.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }
}
