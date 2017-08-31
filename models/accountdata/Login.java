package models.accountdata;

public class Login extends AccountData {

    public Login() {

    }

    public Login(String value) {
        super(value);
    }

    public boolean isValid(){

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
