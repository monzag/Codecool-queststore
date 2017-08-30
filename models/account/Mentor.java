package models.account;


public class Mentor extends Codecooler {

    private Integer classId;

    public Mentor(Integer id, Login login, Password password, Email email, String name, String surname) {
        super(id, login, password, email, name, surname);
        this.classId = null;
    }

    public Mentor(Integer id, Login login, Password password, Email email, String name, String surname. Integer classId) {
        super(id, login, password, email, name, surname);
        this.classId = classId;
    }

    public Integer getClassId() {
        return this.classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }


}
