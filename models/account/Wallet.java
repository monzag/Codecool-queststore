package models.account;


public class Wallet {

    private Integer balance;

    public Integer getBalance() {
        return this.balance;
    }

    public Integer addTo(Integer amount) {
        this.balance += amount;
    }
}
