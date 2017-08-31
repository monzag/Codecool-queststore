package models.containers;

import java.util.ArrayList;
import java.util.Iterator;


public class AdminList<Admin> implements Storable {

    private ArrayList<Admin> admins;

    public AdminList() {
        this.admins = new ArrayList<Admin>();
    }


}
