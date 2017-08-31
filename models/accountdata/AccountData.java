package models.accountdata;

public abstract class AccountData {
    String value;

    public AccountData() {
    }

    public AccountData(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
