package models.containers;

import java.util.ArrayList;
import java.util.Iterator;

import models.account.Admin;


public class AdminList<Admin> implements Storable {

    private ArrayList<Admin> admins;

    public AdminList() {
        this.admins = new ArrayList<Admin>();
    }

    public ArrayList<Admin> getAll() {
        return this.admins;
    }

    public void load() {
        //TODO
    }

    public void save() {
        //TODO
    }

    public boolean add(Admin admin) {
        if (!this.admins.contains(admin)) {
            admins.add(admin);
            return true;
        }
        return false;
    }

    public Iterator getIterator() {
        return this.admins.iterator();
    }

    public boolean remove(Admin admin) {
        return this.admins.remove(admin);
    }


}
