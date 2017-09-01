import controllers.AdminController;
import controllers.AppController;
import models.account.Admin;

public class Main {
    public static void main(String[] args) {
        Admin admin = (Admin)AppController.login();
        if (admin != null) {
            AdminController user = new AdminController(admin);
            user.menu();    
        }
    }
}
