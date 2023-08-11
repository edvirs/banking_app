import java.io.Serializable;
import java.util.ArrayList;

public class Customer{
    private int customerID;
    private String firstName;
    private String lastName;
    private String postalCode;
    private String phoneNumber;
    private String email;
    private String passwordHash;
    private ArrayList<Account> accounts;

    public Customer(int customerID, String firstName, String lastName, String postalCode, String phoneNumber,
                    String email, String passwordHash, ArrayList<Account>accounts) {
        this.customerID = customerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.passwordHash = passwordHash;
        this.accounts = accounts;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    private void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void changePassword(String oldPassword, String newPassword){
        if(PasswordManagement.passwordHashing(oldPassword).equals(getPasswordHash())){
            setPasswordHash(PasswordManagement.passwordHashing(newPassword));
        }
    }

    public void addCheckingAccount(int accountId, boolean overdraftAllowed, double overdraftAmount){
        accounts.add(new CheckingAccount(accountId, overdraftAllowed, overdraftAmount));
    }

    public void addSavingAccount(int accountId, double interestRate){
        accounts.add(new SavingAccount(accountId, interestRate));
    }

    public void addCreditAccount(int accountId, double monthlyInterestRate, double creditLimit){
        accounts.add(new CreditAccount(accountId, monthlyInterestRate, creditLimit));
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }
}
