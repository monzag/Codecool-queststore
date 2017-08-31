package models.accountdata;

public abstract class AccountData {
    String value;

    public AccountData() {
    }

    public AccountData(String value) {
        this.value = value;
    }

    protected static  boolean isLengthValid(String value, Integer minLen, Integer maxLen) {
        if (value.length() > minLen && value.length() < maxLen ) {
            return true;

        }else {
            return false;
        }
    }

    protected static boolean isUnique(String value){
        StudentDAO studentData = new StudenttDAO(value);
        MentorDAO mentorData = new MentorDAO(value);
        AdminDAO adminData = new AdminDAO(value);

        if (!adminData.load().equals(null)){
            return false;

        }else if (!mentorData.load().equals(null)){
            return false;

        }else if (!studentData.load().equals(null)){
            return false;

        } else {
            return true;
        }
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
