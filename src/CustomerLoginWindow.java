import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class CustomerLoginWindow extends JFrame {
    private JPanel panel1;
    private JTextField emailField;
    private JButton loginButton;
    private JTextField passwordField;

    public CustomerLoginWindow(){
        super("Customer");
        init();

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String logInPassHash = PasswordManagement.passwordHashing(passwordField.getText());

                try {
                    String customersPassHash = DataBaseManagement.getPassHash(email);

                    if (logInPassHash.equals(customersPassHash)){
                        CustomerWindow window = new CustomerWindow(email);
                        window.setLocation(400,200);
                        window.setVisible(true);
                        dispose();
                    }else {
                        throw new Exception();
                    }
                }
                catch (Exception e1){
                    JOptionPane.showMessageDialog(null, "Wrong Password or Email. Please, try again", "Error", JOptionPane.WARNING_MESSAGE );
                }

            }
        });
    }

    private void init(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.pack();
    }

}
