public class CheckingAccount extends Account{
    private boolean allowsOverdraft;
    private double overdraftAmount;

    public CheckingAccount(int accountID, boolean allowsOverdraft, double overdraftAmount) {
        this.accountID = accountID;
        this.allowsOverdraft = allowsOverdraft;
        this.overdraftAmount = overdraftAmount;
        this.balance = 0;
        this.accountType = "Checking";
    }

    public boolean isOverdraftAllowed() {
        return allowsOverdraft;
    }

    public void setAllowsOverdraft(boolean allowsOverdraft) {
        this.allowsOverdraft = allowsOverdraft;
    }

    public double getOverdraftAmount() {
        return overdraftAmount;
    }

    public void setOverdraftAmount(double overdraftAmount) {
        this.overdraftAmount = overdraftAmount;
    }

}
