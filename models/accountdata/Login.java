package models.accountdata;

public class Login extends AccountData {

    public Login() {

    }

    public Login(String value) {
        super(value);
    }

    public boolean isValid(){
        boolean isUnique = this.isUnique();
        int valueLen = this.value.length();

        if (valueLen < 20 && isUnique) {
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
}
