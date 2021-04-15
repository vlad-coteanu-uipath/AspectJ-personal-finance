package main.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.io.IOException;

@SpringBootApplication
@EnableAspectJAutoProxy
public class ServerMain implements CommandLineRunner {

    private static final int SERVER_PORT = 6666;

    @Autowired
    private Server server;

    public static void main(String[] args) {
        SpringApplication.run(ServerMain.class, args);
    }

    @Override
    public void run(String... strings) {

        try {
            server.start(SERVER_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
