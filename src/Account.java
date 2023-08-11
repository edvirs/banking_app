import java.io.Serializable;

public class Account implements Serializable {
    protected int accountID;
    protected double balance;

    public String accountType;

    public Account() {
    }

    public int getAccountID() {
        return accountID;
    }

    protected void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public double getBalance() {
        return balance;
    }

    protected void setBalance(double balance) {
        this.balance = balance;
    }

    public void deposit(double value){
        setBalance(getBalance() + value);
    }

    public void withdraw(double value){
        setBalance(getBalance() - value);
    }
    public String getAccountType(){return this.accountType;}

}
