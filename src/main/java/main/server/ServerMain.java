package main.server;

import java.io.IOException;

public class ServerMain {

    private static final int SERVER_PORT = 6666;

    public static void main(String[] args) {

        try {
            new Server().start(SERVER_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
