import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdministratorWindow extends JFrame {
    private JPanel panel1;
    private JButton newCustomerButton;
    private JButton showAllCustomersButton;
    private JButton searchButton;
    private JPanel parentPanel;
    private JPanel newCustomerPanel;
    private JPanel showPanel;
    private JPanel searchPanel;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField postalCodeField;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextField passwordField;
    private JButton editCustomerButton;
    private JPanel editCustomerPanel;
    private JButton createANewCustomerButton;
    private JTextField idField;
    private JTable table1;
    private JScrollPane scrollPanel1;
    private JScrollPane scrollPanel2;
    private JTable table2;
    private JComboBox comboBox1;
    private JTextField searchField;
    private JButton searchButton2;
    private JPanel searchBarPanel;
    private JButton refreshButton;
    private JPanel refreshPanel;
    private JPanel loadPanel;
    private JPanel editParentPanel;
    private JPanel editPanel;
    private JPanel emptyPanel;
    private JTextField emailLoadField;
    private JButton loadCustomerButton;
    private JTextField editFirstNameField;
    private JTextField editIdField;
    private JTextField editLastNameField;
    private JTextField editPostalCodeField;
    private JTextField editPhoneNumberField;
    private JTextField editEmailField;
    private JButton saveChangesButton;
    private JButton cancelButton;
    private JButton deleteCustomerButton;
    private JButton manageCustomerSAccountsButton;
    private JPanel accountsPanel;
    private JButton backButton;
    private JTable accountsTable;
    private JTextField accountIdField;
    private JComboBox accountTypeComboBox;
    private JButton addAccountButton;
    private JButton deleteAccountButton;
    private JPanel setUpCheckingPanel;
    private JPanel setUpSavingPanel;
    private JPanel setUpCreditPanel;
    private JCheckBox overdraftCheckBox;
    private JTextField overdraftField;
    private JButton saveCheckingButton;
    private JTextField interestRateField;
    private JButton saveSavingButton;
    private JTextField creditLimitField;
    private JTextField monthlyInterestRateField;
    private JButton saveCreditButton;
    private Customer customer;

    public AdministratorWindow(){
        super("Administrator");
        init();
        newCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPanel(parentPanel, newCustomerPanel);
            }
        });

        editCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPanel(parentPanel, editCustomerPanel);
            }
        });

        showAllCustomersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPanel(parentPanel, showPanel);
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPanel(parentPanel, searchPanel);
            }
        });

        createANewCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id;
                String firstName;
                String lastName;
                String postalCode;
                String phoneNumber;
                String email;
                String passwordHash;
                boolean parseDone = false;
                boolean idAlreadyExists = false;
                boolean emailAlreadyExists = false;

                try {
                    id = Integer.parseInt(idField.getText());
                    firstName = firstNameField.getText();
                    lastName = lastNameField.getText();
                    postalCode = postalCodeField.getText();
                    phoneNumber = phoneField.getText();
                    email = emailField.getText();
                    passwordHash = PasswordManagement.passwordHashing(passwordField.getText());

                    if(firstName.isBlank() || lastName.isBlank() || postalCode.isBlank() || phoneNumber.isBlank() ||
                        email.isBlank() || email.isBlank() || passwordField.getText().isBlank()){
                        throw new Exception();
                    }

                    parseDone = true;
                }
                catch (Exception e1){
                    JOptionPane.showMessageDialog(null, "Invalid Input", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if(parseDone){
                    idAlreadyExists = (DataBaseManagement.search("id", Integer.toString(id)).size() > 0) ;
                    emailAlreadyExists = (DataBaseManagement.search("email", email).size() > 0);

                    if (idAlreadyExists){
                        JOptionPane.showMessageDialog(null, "ID already exists", "Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }else if(emailAlreadyExists){
                        JOptionPane.showMessageDialog(null, "Email already exists", "Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    resetInputFields();

                    Customer customer = new Customer(id, firstName, lastName, postalCode, phoneNumber, email, passwordHash, new ArrayList<Account>());
                    DataBaseManagement.addCustomer(customer);
                }
            }
        });

        searchButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchBy = String.valueOf(comboBox1.getSelectedItem());
                String criteria = searchField.getText();
                ArrayList<Customer> customers = DataBaseManagement.search(searchBy, criteria);

                if(customers == null || customers.size() == 0 ) {
                    JOptionPane.showMessageDialog(null, "Not Found", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                createCustomersTable(customers, table2);
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createCustomersTable(DataBaseManagement.getAllCustomers(), table1);
            }
        });

        loadCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ArrayList<Customer> customers = DataBaseManagement.search("email", emailLoadField.getText());

                if(customers == null || customers.size() == 0 ) {
                    JOptionPane.showMessageDialog(null, "Not Found", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                customer = customers.get(0);

                editIdField.setText(Integer.toString(customer.getCustomerID()));
                editFirstNameField.setText(customer.getFirstName());
                editLastNameField.setText(customer.getLastName());
                editPostalCodeField.setText(customer.getPostalCode());
                editPhoneNumberField.setText(customer.getPhoneNumber());
                editEmailField.setText(customer.getEmail());

                createAccountsTable(customer.getAccounts(), accountsTable);


                showPanel(editParentPanel, editPanel);

                loadCustomerButton.setEnabled(false);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPanel(editParentPanel, emptyPanel);
                loadCustomerButton.setEnabled(true);
            }
        });

        saveChangesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmation = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to save changes?", "confirmation",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if(confirmation == 0){
                    editCustomer(customer);
                }
            }
        });
        deleteCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmation = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to delete this customer?", "confirmation",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if(confirmation == 0){
                    resetEditFields();
                    emailLoadField.setText("");
                    DataBaseManagement.deleteCustomer(customer.getEmail());
                }
            }
        });

        manageCustomerSAccountsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPanel(editParentPanel, accountsPanel);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPanel(editParentPanel, editPanel);
            }
        });

        addAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountType = accountTypeComboBox.getSelectedItem().toString();
                try {
                    Integer.parseInt(accountIdField.getText());
                }catch (Exception e1){
                    JOptionPane.showMessageDialog(null, "Invalid Input", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                switch (accountType){
                    case "Checking"-> showPanel(editParentPanel, setUpCheckingPanel);
                    case "Saving" -> showPanel(editParentPanel, setUpSavingPanel);
                    case "Credit" -> showPanel(editParentPanel, setUpCreditPanel);
                }
            }
        });

        deleteAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Account> accounts = customer.getAccounts();
                int id;

                try{
                    if(accountIdField.getText().isBlank()) throw new Exception();
                    id = Integer.parseInt(accountIdField.getText());
                }catch (Exception e1){
                    JOptionPane.showMessageDialog(null, "Invalid Input", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                for(int i = 0; i < accounts.size(); i++){
                    if (accounts.get(i).getAccountID() == id){
                        accounts.remove(i);
                    }
                }

                accountIdField.setText("");

                DataBaseManagement.updateAccountsBLOB(customer.getEmail(), customer.getAccounts());

                createAccountsTable(customer.getAccounts(), accountsTable);
            }
        });

        overdraftCheckBox.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                overdraftField.setEnabled(!overdraftField.isEnabled());
            }
        });

        saveCheckingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id;
                double overdraftAmount;
                String accountType = accountTypeComboBox.getSelectedItem().toString();
                try{
                    id = Integer.parseInt(accountIdField.getText());

                    if(!overdraftCheckBox.isSelected()){
                        overdraftAmount = 0;
                    } else overdraftAmount = Double.parseDouble(overdraftField.getText());
                }
                catch (Exception e1){
                    JOptionPane.showMessageDialog(null, "Invalid Input", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                customer.addCheckingAccount(id, overdraftCheckBox.isSelected(), overdraftAmount);

                DataBaseManagement.updateAccountsBLOB(customer.getEmail(), customer.getAccounts());

                createAccountsTable(customer.getAccounts(), accountsTable);

                accountIdField.setText("");

                showPanel(editParentPanel, accountsPanel);
            }
        });

        saveSavingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id;
                double interestRate;
                String accountType = accountTypeComboBox.getSelectedItem().toString();
                try{
                    id = Integer.parseInt(accountIdField.getText());
                    interestRate = Double.parseDouble(interestRateField.getText());
                }
                catch (Exception e1){
                    JOptionPane.showMessageDialog(null, "Invalid Input", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                customer.addSavingAccount(id, interestRate);

                DataBaseManagement.updateAccountsBLOB(customer.getEmail(), customer.getAccounts());

                createAccountsTable(customer.getAccounts(), accountsTable);

                accountIdField.setText("");

                showPanel(editParentPanel, accountsPanel);
            }
        });

        saveCreditButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id;
                double creditLimit;
                double monthlyInterestRate;
                String accountType = accountTypeComboBox.getSelectedItem().toString();
                try{
                    id = Integer.parseInt(accountIdField.getText());
                    creditLimit = Double.parseDouble(creditLimitField.getText());
                    monthlyInterestRate = Double.parseDouble(monthlyInterestRateField.getText());
                }
                catch (Exception e1){
                    JOptionPane.showMessageDialog(null, "Invalid Input", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                customer.addCreditAccount(id, monthlyInterestRate, creditLimit);

                DataBaseManagement.updateAccountsBLOB(customer.getEmail(), customer.getAccounts());

                createAccountsTable(customer.getAccounts(), accountsTable);

                accountIdField.setText("");

                showPanel(editParentPanel, accountsPanel);
            }
        });
    }

    private void init(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.pack();
        searchButton2.setText("\uD83D\uDD0D");
        showPanel(editParentPanel, emptyPanel);
    }

    private void showPanel(JPanel parentPanel, JPanel panel){
        parentPanel.removeAll();
        parentPanel.add(panel);
        parentPanel.repaint();
        parentPanel.revalidate();
    }

    private void resetInputFields(){
        idField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        postalCodeField.setText("");
        phoneField.setText("");
        emailField.setText("");
        passwordField.setText("");
    }

    private void resetEditFields(){
        editIdField.setText("");
        editFirstNameField.setText("");
        editLastNameField.setText("");
        editPostalCodeField.setText("");
        editPhoneNumberField.setText("");
        editEmailField.setText("");
    }

    private boolean XOR(boolean a, boolean b){ //XOR logical operation implementation
        return (a && !b) || (!a && b);
    }

    private void editCustomer(Customer customer){
        int id;
        String firstName;
        String lastName;
        String postalCode;
        String phoneNumber;
        String email;
        String passwordHash;
        boolean parseDone = false;
        boolean idAlreadyExists = false;
        boolean emailAlreadyExists = false;

        try {
            id = Integer.parseInt(editIdField.getText());
            firstName = editFirstNameField.getText();
            lastName = editLastNameField.getText();
            postalCode = editPostalCodeField.getText();
            phoneNumber = editPhoneNumberField.getText();
            email = editEmailField.getText();

            if(firstName.isBlank() || lastName.isBlank() || postalCode.isBlank() || phoneNumber.isBlank() ||
                    email.isBlank() || email.isBlank()){
                throw new Exception();
            }

            parseDone = true;
        }
        catch (Exception e1){
            JOptionPane.showMessageDialog(null, "Invalid Input", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(parseDone){
            idAlreadyExists = XOR(DataBaseManagement.search("id", Integer.toString(id)).size() > 0, id == customer.getCustomerID());
            emailAlreadyExists = XOR(DataBaseManagement.search("email", email).size() > 0, email.equals(customer.getEmail()));

            if (idAlreadyExists){
                JOptionPane.showMessageDialog(null, "ID already exists", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }else if(emailAlreadyExists){
                JOptionPane.showMessageDialog(null, "Email already exists", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            resetEditFields();

            DataBaseManagement.updateInfo(customer.getEmail(), "id", Integer.toString(id));
            DataBaseManagement.updateInfo(customer.getEmail(), "firstName", firstName);
            DataBaseManagement.updateInfo(customer.getEmail(), "lastName", lastName);
            DataBaseManagement.updateInfo(customer.getEmail(), "phoneNumber", phoneNumber);
            DataBaseManagement.updateInfo(customer.getEmail(), "email", email);

            JOptionPane.showMessageDialog(null, "Successfully saved", "Done", JOptionPane.INFORMATION_MESSAGE);

        }
    }


    private void createCustomersTable(ArrayList<Customer> customers, JTable table){
        if (customers == null) return;

        Object[][] data = new Object[customers.size()][6];

        for (int i = 0; i < customers.size(); i++){
            data[i][0] = customers.get(i).getCustomerID();
            data[i][1] = customers.get(i).getFirstName();
            data[i][2] = customers.get(i).getLastName();
            data[i][3] = customers.get(i).getPostalCode();
            data[i][4] = customers.get(i).getPhoneNumber();
            data[i][5] = customers.get(i).getEmail();
        }

        table.setModel(new DefaultTableModel(
                data,
                new String[]{"ID", "FirstName", "Last Name", "Postal Code", "Phone Number", "Email"}
        ));
        TableColumnModel columns = table.getColumnModel();
        columns.getColumn(0).setMaxWidth(30);
        columns.getColumn(1).setMaxWidth(150);
        columns.getColumn(2).setMaxWidth(150);
        columns.getColumn(3).setMaxWidth(90);
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
