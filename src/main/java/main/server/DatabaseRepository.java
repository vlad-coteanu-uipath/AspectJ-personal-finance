package main.server;

import main.common.entities.Category;
import main.common.entities.Expense;
import main.common.entities.User;

import java.util.List;

public interface DatabaseRepository {

    Category registerCategory(Category category);

    List<Category> getAllCategories(int userId);

    Expense registerExpense(Expense expense);

    List<Expense> getAllExpenses(int userId);

    User loginUser(User user);

}
