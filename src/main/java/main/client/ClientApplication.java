package main.client;

import main.client.SwingApp.ApplicationMainFrame;
import main.client.SwingApp.LoginDialog;
import main.client.SwingApp.LoginListener;
import main.server.ServerMain;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

@SpringBootApplication
@EnableAspectJAutoProxy
public class ClientApplication implements CommandLineRunner {

    @Autowired
    private Client client;

    private ApplicationMainFrame applicationMainFrame;

    @Autowired
    private BeanFactory beanFactory;

    private static final int DEFAULT_CLIENT_SOCKET = 6666;
    private static final String DEFAULT_CLIENT_IP = "127.0.0.1";

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(ClientApplication.class);

        builder.headless(false);

        ConfigurableApplicationContext context = builder.run(args);
    }


    @Override
    public void run(String... args) {
        try {
            client.startConnection(DEFAULT_CLIENT_IP, DEFAULT_CLIENT_SOCKET);
            // if (!ClientCache.getInstance().isServerConnected()) {
            //     return;
            //}
            LoginListener loginListener = new LoginListener() {
                @Override
                public void onSuccessfulLogin() {
                    applicationMainFrame = beanFactory.getBean(ApplicationMainFrame.class);
                    applicationMainFrame.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e)
                        {
                            e.getWindow().dispose();
                            try {
                                client.stopConnection();
                                System.exit(0);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    });
                }
            };
            String loginDialogTitle = "Login to system";
            LoginDialog ld = beanFactory.getBean(LoginDialog.class, null, loginDialogTitle, true, loginListener);
            ld.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Server is not running");
        }
    }
}
