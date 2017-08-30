package models.account;


public class Student extends Codecooler {

    private Integer classId;
    private Integer teamId;
    private Wallet wallet;

    public Student(Integer id, Login login, Password password, Email email, String name, String surname, Wallet wallet) {

        super(id, login, password, email, name, surname);
        this.classId = null;
        this.teamId = null;
        this.wallet = wallet;

    }

    public Student(Integer id, Login login, Password password, Email email, String name, String surname, Integer classId, Wallet wallet) {

        super(id, login, password, email, name, surname);
        this.classId = classId;
        this.teamId = null;
        this.wallet = wallet;

    }

    public Student(Integer id, Login login, Password password, Email email, String name, String surname, Integer classId, Integer teamId, Wallet wallet) {

        super(id, login, password, email, name, surname);
        this.classId = classId;
        this.teamId = teamId;
        this.wallet = wallet;

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

    public boolean pay(Integer amount) {
        // try to take money of wallet if suceed return true
        return this.wallet.take(amount);
    }

}
