package models.account;

import models.accountdata.Mail;
import models.accountdata.Login;
import models.accountdata.Password;


public class Admin extends Codecooler {

    public Admin() {
        
    }

    public Admin(Login login, Password password, Mail email, String name, String surname) {
        super(login, password, email, name, surname);
    }


}
