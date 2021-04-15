package main.client;

import main.common.entities.Category;
import main.common.entities.Expense;
import main.common.entities.User;
import main.common.messaging.Message;
import main.common.messaging.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

@Component
public class Client {

    @Autowired
    private RequestSender requestSender;

    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public Client() {}

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());
    }

    public List<Expense> getAllExpenses() {
        try {
            Message resp = requestSender.sendMessage(
                    out,
                    in,
                    new Message(MessageType.GET_EXPENSES_REQUEST, ClientCache.getInstance().getLoggedUser().getId()));

            if(resp.payload != null) {
                return (List<Expense>) resp.payload;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<Category> getAllCategories() {
        try {
            Message resp = requestSender.sendMessage(
                    out,
                    in,
                    new Message(MessageType.GET_CATEGORIES_REQUEST, ClientCache.getInstance().getLoggedUser().getId()));

            if(resp.payload != null) {
                return (List<Category>) resp.payload;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public Category registerNewCategory(Category category) {
        try {
            Message resp = requestSender.sendMessage(
                    out,
                    in,
                    new Message(category, MessageType.REGISTER_CATEGORY_REQUEST, ClientCache.getInstance().getLoggedUser().getId()));

            if(resp.payload != null) {
                return (Category) resp.payload;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Expense registerNewExpense(Expense category) {
        try {
            Message resp = requestSender.sendMessage(
                    out,
                    in,
                    new Message(category, MessageType.REGISTER_EXPENSE_REQUEST, ClientCache.getInstance().getLoggedUser().getId()));

            if(resp.payload != null) {
                return (Expense) resp.payload;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User loginUser(User user) {
        try {
            Message resp = requestSender.sendMessage(
                    out,
                    in,
                    new Message(user, MessageType.LOG_USER_REQUEST));

            if(resp.payload != null) {
                return (User) resp.payload;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
}
