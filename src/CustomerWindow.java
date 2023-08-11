import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CustomerWindow extends JFrame{
    private JPanel panel1;
    private JButton profileButton;
    private JButton accountsButton;
    private JButton transfersButton;
    private JPanel parentPanel;
    private JPanel profilePanel;
    private JPanel accountsPanel;
    private JPanel transfersPanel;
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JLabel postalCodeLabel;
    private JLabel phoneNumberLabel;
    private JLabel emailLabel;
    private JLabel idLabel;
    private JPanel infoPanel;
    private JPanel changePassPanel;
    private JButton changeButton;
    private JPasswordField oldPass;
    private JPasswordField newPass;
    private JPanel welcomePanel;
    private JLabel nameLabel;
    private JLabel welcomeLabel;
    private JTable accountsTable;
    private JTextField accountIdField;
    private JTextField ammountField;
    private JButton depoditButton;
    private JButton withdrawButton;

    private Customer customer;

    public CustomerWindow(String email){
        customer = DataBaseManagement.search("email", email).get(0);
        init(customer);

        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPanel(parentPanel, profilePanel);
            }
        });

        accountsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPanel(parentPanel, accountsPanel);

                ArrayList<Account>accounts = DataBaseManagement.search("email", customer.getEmail()).get(0).getAccounts();;
                createAccountsTable(accounts, accountsTable);
            }
        });

        transfersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPanel(parentPanel, transfersPanel);
            }
        });

        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(oldPass.getText().isBlank() || newPass.getText().isBlank()){
                    JOptionPane.showMessageDialog(null, "Invalid Input", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (customer.getPasswordHash().equals(PasswordManagement.passwordHashing(oldPass.getText()))){
                    DataBaseManagement.updateInfo(email, "passwordHash", PasswordManagement.passwordHashing(newPass.getText()));
                    JOptionPane.showMessageDialog(null, "Password updated successfully", "Done", JOptionPane.INFORMATION_MESSAGE);
                    oldPass.setText("");
                    newPass.setText("");
                }else {
                    JOptionPane.showMessageDialog(null, "Wrong Old Password", "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        depoditButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id;
                double amount;

                try{
                    id = Integer.parseInt(accountIdField.getText());
                    amount = Double.parseDouble(ammountField.getText());
                } catch (Exception e1){
                    JOptionPane.showMessageDialog(null, "Invalid Input", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                for(Account account: customer.getAccounts()) {
                    if (account.getAccountID() == id){
                        account.deposit(amount);
                    }
                }

                ammountField.setText("");
                accountIdField.setText("");
                DataBaseManagement.updateAccountsBLOB(customer.getEmail(), customer.getAccounts());
                createAccountsTable(customer.getAccounts(), accountsTable);
            }
        });
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id;
                double amount;

                try{
                    id = Integer.parseInt(accountIdField.getText());
                    amount = Double.parseDouble(ammountField.getText());
                } catch (Exception e1){
                    JOptionPane.showMessageDialog(null, "Invalid Input", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                for(Account account: customer.getAccounts()) {
                    if (account.getAccountID() == id){
                        account.withdraw(amount);
                    }
                }

                ammountField.setText("");
                accountIdField.setText("");
                DataBaseManagement.updateAccountsBLOB(customer.getEmail(), customer.getAccounts());
                createAccountsTable(customer.getAccounts(), accountsTable);
            }
        });
    }

    private void showPanel(JPanel parentPanel, JPanel panel){
        parentPanel.removeAll();
        parentPanel.add(panel);
        parentPanel.repaint();
        parentPanel.revalidate();
    }

    private void init(Customer customer){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.pack();

        nameLabel.setText(customer.getFirstName() + " " + customer.getLastName());
        idLabel.setText(String.format("ID: %s", customer.getCustomerID()));
        firstNameLabel.setText(String.format("First Name: %s", customer.getFirstName()));
        lastNameLabel.setText(String.format("Last Name: %s", customer.getLastName()));
        postalCodeLabel.setText(String.format("Postal Code: %s", customer.getPostalCode()));
        phoneNumberLabel.setText(String.format("Phone Number: %s", customer.getPhoneNumber()));
        emailLabel.setText(String.format("Email: %s", customer.getEmail()));

        showPanel(parentPanel, welcomePanel);
    }

    private void createAccountsTable(ArrayList<Account> accounts, JTable table){
        if (accounts == null) return;

        Object[][] data = new Object[accounts.size()][3];

        for (int i = 0; i < accounts.size(); i++){
            data[i][0] = accounts.get(i).getAccountID();
            data[i][1] = accounts.get(i).getAccountType();
            data[i][2] = accounts.get(i).getBalance();
        }

        table.setModel(new DefaultTableModel(
                data,
                new String[]{"ID", "Type", "Balance"}
        ));
    }

}
