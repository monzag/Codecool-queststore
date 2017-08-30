package models.account;


public abstract class Codecooler {

    protected Integer id;
    protected Login login;
    protected Password password;
    protected Email email;
    protected String name;
    protected String surname;

    public Codecooler() {

    }

    public Codecooler(Integer id, Login login, Password password, Email email, String name, String surname) {

        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.name = name;
        this.surname = surname;

    }

    public Integer getId() {
        return this.id;
    }
    public Login getLogin() {
        return this.login;
    }
    public Password getPassword() {
        return this.password;
    }
    public Email getEmail() {
        return this.email;
    }
    public String getName() {
        return this.name;
    }
    public String getSurname() {
        return this.surname;
    }


}
