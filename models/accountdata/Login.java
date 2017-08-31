package models.accountdata;

public class Login extends AccountData {

    public Login() {

    }

    public Login(String value) {
        super(value);
    }

    public static boolean isValid(String value){
        boolean isUnique = isUnique(value);
        boolean isLengthValid = isLengthValid(value, 5, 20);

        if (isLengthValid && isUnique) {
            return true;

        }else {
            return false;
        }
    }

    private static boolean isUnique(String value){
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
}
