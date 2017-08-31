package models.accountdata;

public class Login extends AccountData {

    public Login() {

    }

    public Login(String value) {
        super(value);
    }

    public static boolean isValid(String value){
        boolean isUnique = isUnique(value);
        boolean isLengthValid = isLengthValid(value);

        if (isLengthValid && isUnique) {
            return true;

        }else {
            return false;
        }
    }

    private static boolean isUnique(String value){
        AccountDAO accountData = new AccountDAO(value);

        if (!accountData.load().equals(null)){
            return false;
        }else {
            return true;
        }
    }

    private static  boolean isLengthValid(String value) {
        if (value.length() > 20 && value.length() < 20 ) {
            return true;
        }else {
            return false;
        }
    }
}
