package main.server;

import main.common.entities.Category;
import main.common.entities.Expense;
import main.common.entities.User;
import main.common.messaging.Message;
import main.common.messaging.MessageType;
import main.server.repository.DatabaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RequestHandler {

    private DatabaseRepository repository;

    public RequestHandler(@Qualifier("binFileDatabaseRepositoryImpl") DatabaseRepository repository) {

        this.repository = repository;

        if(!repository.isDBReady()) {
            throw new RuntimeException("Database is not ready");
        }
    }

    public Message handleRequest(ServerClientHandler sender, Message message) {

        double chance = Math.random();
        if(chance < 0.20) {
            System.out.println("Server random communication error. Sending null to client. Error chance: " + chance);
            return null;
        }

        try {
            switch (message.messageType) {
                case LOG_USER_REQUEST:
                    User respUser = repository.loginUser((User) message.payload);
                    return createSuccessMessage(respUser);
                case REGISTER_CATEGORY_REQUEST:
                    Category category = repository.registerCategory((Category) message.payload);
                    return createSuccessMessage(category);
                case REGISTER_EXPENSE_REQUEST:
                    Expense expense = repository.registerExpense((Expense) message.payload);
                    return createSuccessMessage(expense);
                case GET_CATEGORIES_REQUEST:
                    List<Category> categories = repository.getAllCategories(message.userId);
                    return createSuccessMessage(categories);
                case GET_EXPENSES_REQUEST:
                    List<Expense> expenses = repository.getAllExpenses(message.userId);
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
