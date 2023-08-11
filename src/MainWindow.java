import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    private JPanel panel1;
    private JButton admisistratorButton;
    private JButton customerButton;

    public MainWindow(){
        init();
        Point location = this.getLocation();
        admisistratorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdministratorWindow window = new AdministratorWindow();
                window.setLocation(location);
                window.setVisible(true);
                dispose();
            }
        });
        customerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomerLoginWindow window = new CustomerLoginWindow();
                window.setLocation(location);
                window.setVisible(true);
                dispose();
            }
        });
    }

    private void init(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.setLocation(400, 200);
        this.pack();
    }
}
