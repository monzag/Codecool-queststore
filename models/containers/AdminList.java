package models.containers;


public class AdminList<Admin> implements Storable {

    private ArrayList<Admin> admins;

    public AdminList() {
        this.admins = new ArrayList<Admin>();
    }
}
