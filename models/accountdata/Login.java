package models.accountdata;

public class Login extends AccountData {
    private static final Integer LOGINMAXLEN = 20;
    private static final Integer LOGINMINLEN = 5;

    public Login() {

    }

    public Login(String value) {
        super(value);
    }

    public static boolean isValid(String value){
        boolean isUnique = isUnique(value);
        boolean isLengthValid = isLengthValid(value, LOGINMINLEN, LOGINMAXLEN);

        if (isLengthValid && isUnique) {
            return true;

        }else {
            return false;
        }
    }
}
