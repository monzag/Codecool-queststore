package models.containers;

import java.util.ArrayList;
import java.util.Iterator;

import models.account.Admin;


public class AdminList<Admin> implements Storable {

    private ArrayList<Admin> admins;

    public AdminList() {
        this.admins = new ArrayList<Admin>();
    }

    public Iterator getIterator() {
        return this.admins.iterator();
    }


}
