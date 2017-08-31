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

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
