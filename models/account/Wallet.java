package models.account;


public class Wallet {

    private Integer balance;

    public Wallet() {
        this.balance = 0;
    }

    public Wallet(Integer balance) {
        this.balance = balance;
    }

    public Integer getBalance() {
        return this.balance;
    }

    public void put(Integer amount) {
        this.balance += amount;
    }

    private boolean has(Integer amount) {
        return this.balance >= amount;
    }

    public boolean take(Integer amount) {
        if (this.has(amount)) {
            this.balance -= amount;
            return true;
        }
        return false
    }


}
