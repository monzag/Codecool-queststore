package models.account;


public class Admin extends Codecooler {

    public Admin(Integer id, Login login, Password password, Email email, String name, String surname) {
        super(id, login, password, email, name, surname);
    }
}
