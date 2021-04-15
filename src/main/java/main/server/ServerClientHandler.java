package main.server;

import main.common.entities.Category;
import main.common.entities.Expense;
import main.common.entities.User;
import main.common.messaging.Message;
import main.common.messaging.MessageType;
import main.server.repository.DatabaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sun.misc.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ServerClientHandler extends Thread {

    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    @Autowired
    RequestHandler requestHandler;

    public ServerClientHandler() {
    }

    public ServerClientHandler setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
        return this;
    }

    @Override
    public String toString() {
        return clientSocket != null ? clientSocket.getRemoteSocketAddress().toString() : "";
    }

    @Override
    public synchronized void start() {
        super.start();
    }

    public void run() {
        try {
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());

            Message request;
            while ((request = (Message) in.readObject()) != null) {
                Message response = requestHandler.handleRequest(this, request);
                out.writeObject(response);
            }

            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException | ClassNotFoundException e) {
        }
    }
}
