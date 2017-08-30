package models.account;


public class Student extends Codecooler {

    private Integer classId;
    private Integer teamId;

    public Student(Integer id, Login login, Password password, Email email, String name, String surname) {
        super(id, login, password, email, name, surname);
        this.classId = null;
        this.teamId = null;
    }

    public Student(Integer id, Login login, Password password, Email email, String name, String surname, Integer classId) {
        super(id, login, password, email, name, surname);
        this.classId = classId;
        this.teamId = null;
    }

    public Student(Integer id, Login login, Password password, Email email, String name, String surname, Integer classId, Integer teamId) {
        super(id, login, password, email, name, surname);
        this.classId = classId;
        this.teamId = teamId;
    }

    public Integer getClassId() {
        return this.classId;
    }
    public Integer getTeamId() {
        return this.teamId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }
    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

}
