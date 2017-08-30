package models.account;


public class Mentor extends Codecooler {

    private Integer classId;

    public Menotr(Integer id, Login login, Password password, Email email, String name, String surname) {
        super(id, login, password, email, name, surname);
        this.classId = null;
    }

}
