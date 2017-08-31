package models.accountdata;

public class Login extends AccountData {

    public Login() {

    }

    public Login(String value) {
        super(value);
    }

    public static boolean isValid(String value){
        boolean isUnique = isUnique(value);
        boolean isLengthValid = this.isLengthValid(value);

        if (isLengthValid && isUnique) {
            return true;

        }else {
            return false;
        }
    }

    public boolean isUnique(){
        AccountDAO accountData = new AccountDAO(this.value);

        if (!accountData.load().equals(null)){
            return false;
        }else {
            return true;
        }
    }

    private boolean isLengthValid() {
        if (this.value.length() > 20 && this.value.length() < 20 ) {
            return true;
        }else {
            return false;
        }
    }
}
