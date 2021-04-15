package main.client;

import main.client.SwingApp.ApplicationMainFrame;
import main.client.SwingApp.LoginDialog;
import main.client.SwingApp.LoginListener;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;


public class ClientApplication {

    private static final int DEFAULT_CLIENT_SOCKET = 6666;
    private static final String DEFAULT_CLIENT_IP = "127.0.0.1";
    private static ApplicationMainFrame applicationMainFrame;


    public static void main(String[] args) {
        try {
            Client.getInstance().startConnection(DEFAULT_CLIENT_IP, DEFAULT_CLIENT_SOCKET);
            // if (!ClientCache.getInstance().isServerConnected()) {
            //     return;
            //}
            LoginListener loginListener = new LoginListener() {
                @Override
                public void onSuccessfulLogin() {
                    applicationMainFrame = new ApplicationMainFrame();
                    applicationMainFrame.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e)
                        {
                            e.getWindow().dispose();
                            try {
                                Client.getInstance().stopConnection();
                                System.exit(0);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    });
                }
            };
            LoginDialog ld = new LoginDialog(null, "Login to system", true, loginListener);
            ld.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Server is not running");
        }
    }


}
