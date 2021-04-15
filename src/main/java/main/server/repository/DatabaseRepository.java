package main.server.repository;

import main.common.entities.Category;
import main.common.entities.Expense;
import main.common.entities.User;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface DatabaseRepository {

    boolean isDBReady();

    Category registerCategory(Category category);

    List<Category> getAllCategories(int userId);

    Expense registerExpense(Expense expense);

    List<Expense> getAllExpenses(int userId);

    User loginUser(User user);

}
