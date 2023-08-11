public class SavingAccount extends Account{
    private double interestRate;

    public SavingAccount(int accountID, double interestRate) {
        this.accountID = accountID;
        this.interestRate = interestRate;
        this.balance = 0;
        this.accountType = "Saving";
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
}
