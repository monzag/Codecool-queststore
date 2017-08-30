package models.account;


public class Student extends Codecooler {

    private Integer classId;
    private Integer teamId;

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
