public class CreditAccount extends Account{
    private double monthlyInterestRate;
    private double creditLimit;

    public CreditAccount(int accountID, double monthlyInterestRate, double creditLimit) {
        this.accountID = accountID;
        this.monthlyInterestRate = monthlyInterestRate;
        this.creditLimit = creditLimit;
        this.balance = creditLimit;
        this.accountType = "Credit";
    }

    public double getMonthlyInterestRate() {
        return monthlyInterestRate;
    }

    public void setMonthlyInterestRate(double monthlyInterestRate) {
        this.monthlyInterestRate = monthlyInterestRate;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }
}
