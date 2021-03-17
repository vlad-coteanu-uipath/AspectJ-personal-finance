package main.server;

import main.common.entities.Category;
import main.common.entities.Expense;
import main.common.entities.User;
import main.common.messaging.Message;
import main.common.messaging.MessageType;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {

    private ServerSocket serverSocket;

    private DatabaseRepository databaseRepository;

    public Server() {
        databaseRepository = BinFileDatabaseRepositoryImpl.getInstance();
    }

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);

        while (true)
            new ServerClientHandler(serverSocket.accept()).start();
    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    public class ServerClientHandler extends Thread {
        private Socket clientSocket;
        private ObjectOutputStream out;
        private ObjectInputStream in;

        public ServerClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public String toString() {
            return clientSocket != null ? clientSocket.getRemoteSocketAddress().toString() : "";
        }

        public void run() {
            try {
                out = new ObjectOutputStream(clientSocket.getOutputStream());
                in = new ObjectInputStream(clientSocket.getInputStream());

                Message request;
                while ((request = (Message) in.readObject()) != null) {
                    Message response = handleRequest(request);
                    out.writeObject(response);
                }

                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException | ClassNotFoundException e) {
            }
        }

        private Message handleRequest(Message message) {
            try {
                switch (message.messageType) {
                    case LOG_USER_REQUEST:
                        User respUser = databaseRepository.loginUser((User) message.payload);
                        return createSuccessMessage(respUser);
                    case REGISTER_CATEGORY_REQUEST:
                        Category category = databaseRepository.registerCategory((Category) message.payload);
                        return createSuccessMessage(category);
                    case REGISTER_EXPENSE_REQUEST:
                        Expense expense = databaseRepository.registerExpense((Expense) message.payload);
                        return createSuccessMessage(expense);
                    case GET_CATEGORIES_REQUEST:
                        List<Category> categories = databaseRepository.getAllCategories(message.userId);
                        return createSuccessMessage(categories);
                    case GET_EXPENSES_REQUEST:
                        List<Expense> expenses = databaseRepository.getAllExpenses(message.userId);
                        return createSuccessMessage(expenses);
                    default:
                        System.out.println("Request type not identified");
                        return createErrorMessage(null);
                }
            } catch (Exception e) {
                return createErrorMessage(null);
            }
        }

        private Message createErrorMessage(Object payload) {
            return new Message(payload, MessageType.ERROR);
        }

        private Message createSuccessMessage(Object payload) {
            return new Message(payload, MessageType.RESPONSE);
        }
    }

}
