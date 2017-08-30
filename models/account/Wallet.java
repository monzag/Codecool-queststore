package models.account;


public class Wallet {

    private Integer balance;

    public Integer getBalance() {
        return this.balance;
    }

    public Integer addTo(Integer amount) {
        this.balance += amount;
    }

    public boolean has(Integer amount) {
        return this.balance >= amount;
    }


}
