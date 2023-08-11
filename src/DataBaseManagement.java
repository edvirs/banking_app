import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class DataBaseManagement {
    public static Connection getConnection(){
        String jdbcURL = "jdbc:sqlite:base.db";

        try{
            Connection connection = DriverManager.getConnection(jdbcURL);
            String sql = "CREATE TABLE IF NOT EXISTS customersDB ( id INTEGER, firstName TEXT, lastName TEXT," +
                         " postalCode TEXT, phoneNumber TEXT, email TEXT, passwordHash TEXT, accounts BLOB)";

            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            return connection;

        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void closeConnection(Connection connection){
        if (connection != null){
            try {
                connection.close();
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    public static void addCustomer(Customer customer){
        Connection connection = getConnection();

        String sql = String.format("INSERT INTO customersDB values (%d, '%s', '%s','%s', '%s', '%s', '%s', ?)", customer.getCustomerID(), customer.getFirstName(),
                customer.getLastName(), customer.getPostalCode(), customer.getPhoneNumber(), customer.getEmail(), customer.getPasswordHash());

        try{
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setBytes(1, BLOBmanagement.AccountsToBinaryArray(customer.getAccounts()));

            statement.executeUpdate();
            closeConnection(connection);
            JOptionPane.showMessageDialog(null, "Added Successfully", "Done", JOptionPane.INFORMATION_MESSAGE);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            closeConnection(connection);
        }
    }

    public static void updateInfo(String email, String toBeUpdated, String newData){
        Connection connection = getConnection();
        String sql;

        if(toBeUpdated.equals("id")){
            sql = String.format("UPDATE customersDB SET %s = %s WHERE email = '%s'", toBeUpdated, newData, email);
        }else {
            sql = String.format("UPDATE customersDB SET %s = '%s' WHERE email = '%s'", toBeUpdated, newData, email);
        }

        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            closeConnection(connection);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            closeConnection(connection);
        }
    }

    public static void updateAccountsBLOB(String email, ArrayList<Account> accounts){
        Connection connection = getConnection();
        String sqlUpdate = String.format("UPDATE customersDB SET accounts = ? WHERE email = '%s'", email);

        try {

            PreparedStatement statement = connection.prepareStatement(sqlUpdate);

            statement.setBytes(1, BLOBmanagement.AccountsToBinaryArray(accounts));
            statement.executeUpdate();

            closeConnection(connection);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            closeConnection(connection);
        }
    }

    public static void deleteCustomer(String email){
        Connection connection = getConnection();
        String sql = String.format("DELETE FROM customersDB where email = '%s'", email);
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Deleted Successfully", "Done", JOptionPane.INFORMATION_MESSAGE);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            closeConnection(connection);
        }
    }

    public static ArrayList<Customer> getCustomers(String sqlQuery){
        ArrayList<Customer> customers = new ArrayList<Customer>();
        Connection connection = getConnection();

        int id;
        String firstName;
        String lastName;
        String postalCode;
        String phoneNumber;
        String email;
        String passwordHash;
        ArrayList<Account> accounts;


        Customer customer;

        try {
            Statement statement = connection.createStatement();
            ResultSet data = statement.executeQuery(sqlQuery);

            while (data.next()){
                id = data.getInt("id");
                firstName = data.getString("firstName");
                lastName = data.getString("lastName");
                postalCode = data.getString("postalCode");
                phoneNumber = data.getString("phoneNumber");
                email = data.getString("email");
                passwordHash = data.getString("passwordHash");
                byte[] bytes = data.getBytes("accounts");
                accounts = BLOBmanagement.BinaryArrayToAccounts(bytes);


                customer = new Customer(id, firstName, lastName, postalCode, phoneNumber, email, passwordHash, accounts);
                customers.add(customer);
            }
            closeConnection(connection);

            return customers;
        }
        catch (Exception e){
            closeConnection(connection);
            return null;
        }
    }

    public static ArrayList<Customer> getAllCustomers(){
        String sqlQuery = "SELECT * FROM customersDB";

        return getCustomers(sqlQuery);
    }

    public static String getPassHash(String email){
        String sqlQuery = String.format("SELECT passwordHash FROM customersDB WHERE email = '%s'", email);
        String passwordHash = DataBaseManagement.search("email", email).get(0).getPasswordHash();

        return passwordHash;
    }

    public static ArrayList<Customer> search(String searchBy, String criteria){
        Connection connection = getConnection();

        String sqlQuery;

        if (searchBy.equals("id")){
            sqlQuery = String.format("SELECT * FROM customersDB WHERE %s = %s", searchBy, criteria);
        }
        else {
            sqlQuery = String.format("SELECT * FROM customersDB WHERE %s = '%s'", searchBy, criteria);
        }

        ArrayList<Customer> customers = getCustomers(sqlQuery);
        closeConnection(connection);

        return customers;
    }

    public static String getData(Connection connection){
        String data = "";
        String sqlQuery = "SELECT rowid, * FROM customersDB";

        try {
            Statement statement = connection.createStatement();
            ResultSet dataRow = statement.executeQuery(sqlQuery);

            while (dataRow.next()){
                data += dataRow.getInt("rowid") + " | " + dataRow.getString("id").toString() + " " + dataRow.getString("firstName") + " " + dataRow.getString("email") + "\n";
            }
            return data;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
