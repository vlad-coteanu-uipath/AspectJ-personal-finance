package main.server;

import main.server.repository.BinFileDatabaseRepositoryImpl;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.ServerSocket;

@Component
public class Server {

    @Autowired
    private BeanFactory beanFactory;

    private ServerSocket serverSocket;


    public Server() {

    }

    public void start(int port) throws IOException {

        serverSocket = new ServerSocket(port);

        while (true)
        {
            beanFactory.getBean(ServerClientHandler.class)
                .setClientSocket(serverSocket.accept()).start();
        }
    }

    public void stop() throws IOException {
        serverSocket.close();
    }

}
