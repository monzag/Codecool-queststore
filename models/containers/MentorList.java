package models.containers;

import java.util.ArrayList;
import java.util.Iterator;

import models.account.Mentor;


public class MentorList<Mentor> implements Storable {

    private ArrayList<Mentor> mentors;

    public MentorList() {
        this.mentors = new ArrayList<Mentor>();
    }

    public ArrayList<Mentor> getAll() {
        return this.mentors;
    }

    public void load() {
        //TODO
    }

    public void save() {
        //TODO
    }

    public boolean add(Mentor mentor) {
        if (!this.mentors.contains(mentor)) {
            mentors.add(mentor);
            return true;
        }
        return false;
    }

    public Iterator getIterator() {
        return this.mentors.iterator();
    }

    public boolean remove(Mentor mentor) {
        return this.mentors.remove(mentor);
    }

}
