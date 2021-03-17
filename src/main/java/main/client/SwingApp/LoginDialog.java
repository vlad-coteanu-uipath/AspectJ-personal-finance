/*
 * Created by JFormDesigner on Wed Mar 10 10:09:52 EET 2021
 */

package main.client.SwingApp;

import main.client.Client;
import main.client.ClientCache;
import main.common.entities.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.*;

/**
 * @author unknown
 */
public class LoginDialog extends JDialog {

    private LoginListener loginListener;

    public LoginDialog(JFrame owner, String name, boolean isModal, LoginListener loginListener) {
        super(owner, name, isModal);
        this.loginListener = loginListener;
        initComponents();
        initCustomComponents();
    }

    private void initCustomComponents() {
        this.setSize(400, 400);
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                Object[] options = {"Yes, close the application",
                        "No, go back to login"};
                int result = JOptionPane.showOptionDialog(LoginDialog.this,
                        "You can't access the application without authenticating. Do you want to exit the app?",
                        "Exit the app",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[1]);
                if(result == JOptionPane.YES_OPTION) {
                    we.getWindow().dispose();
                    try {
                        Client.getInstance().stopConnection();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.exit(0);
                }
            }
        });

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userNameField.getText();
                String password = new String(passwordField.getPassword());
                if(username != null) {
                    User respUser = Client.getInstance().loginUser(new User(-1, username, password));
                    if(respUser != null) {
                        ClientCache.getInstance().storeUser(respUser);
                        LoginDialog.this.loginListener.onSuccessfulLogin();
                        System.out.println("Client logged in successful");
                        LoginDialog.this.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(LoginDialog.this,
                                "Username or password is wrong. Try again!",
                                "Login error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        label8 = new JLabel();
        label12 = new JLabel();
        userNameField = new JTextField();
        label18 = new JLabel();
        passwordField = new JPasswordField();
        label29 = new JLabel();
        button1 = new JButton();

        //======== LoginDialog ========
        {
            Container LoginDialogContentPane = this.getContentPane();
            LoginDialogContentPane.setLayout(new GridLayout(8, 1));

            //---- label8 ----
            label8.setText("Login");
            label8.setFont(label8.getFont().deriveFont(label8.getFont().getSize() + 4f));
            LoginDialogContentPane.add(label8);

            //---- label12 ----
            label12.setText("Username");
            LoginDialogContentPane.add(label12);
            LoginDialogContentPane.add(userNameField);

            //---- label18 ----
            label18.setText("Password");
            LoginDialogContentPane.add(label18);
            LoginDialogContentPane.add(passwordField);
            LoginDialogContentPane.add(label29);

            //---- button1 ----
            button1.setText("Login");
            LoginDialogContentPane.add(button1);
            this.pack();
            this.setLocationRelativeTo(this.getOwner());
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }



    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JLabel label8;
    private JLabel label12;
    private JTextField userNameField;
    private JLabel label18;
    private JPasswordField passwordField;
    private JLabel label29;
    private JButton button1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
