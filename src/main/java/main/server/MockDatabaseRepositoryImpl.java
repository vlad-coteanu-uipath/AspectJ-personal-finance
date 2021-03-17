package main.server;


import main.common.entities.Category;
import main.common.entities.Expense;
import main.common.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MockDatabaseRepositoryImpl implements DatabaseRepository {

    private static final MockDatabaseRepositoryImpl INSTANCE = new MockDatabaseRepositoryImpl();
    private List<Category> categoryList;
    private List<Expense> expensesList;
    private List<User> usersList;

    public MockDatabaseRepositoryImpl(){

        categoryList = new ArrayList<>();
        expensesList = new ArrayList<>();
        usersList = new ArrayList<>();

        populateWithMockData();
    }

    private void populateWithMockData() {

        usersList.add(new User(1, "vlad.coteanu", "parola"));

        categoryList.add(new Category(1, "Food", 1));
        categoryList.add(new Category(2, "Beverages", 1));
        categoryList.add(new Category(3, "Shopping", 1));

        expensesList.add(new Expense(1, "Coca Cola", 2, 1, 7));
        expensesList.add(new Expense(2, "Fanta", 2, 1, 6));
        expensesList.add(new Expense(3, "Denim Jeans", 3, 1, 250));
        expensesList.add(new Expense(4, "Chicken Wings", 1, 1, 24));
    }

    public Category registerCategory(Category category) {
        category.setId(categoryList.size());
        categoryList.add(category);
        return category;
    };

    public List<Category> getAllCategories(int userId) {
        return categoryList.stream().filter(p->p.getUserId() == userId).collect(Collectors.toList());
    };

    public Expense registerExpense(Expense expense) {
        expense.setId(expensesList.size());
        expensesList.add(expense);
        return expense;
    }

    public List<Expense> getAllExpenses(int userId) {
        return expensesList.stream().filter(p->p.getUserId() == userId).collect(Collectors.toList());
    }

    public User loginUser(User user) {
        for(int i = 0; i < usersList.size(); i ++) {
            User currentUser = usersList.get(i);
            if(currentUser.getUsername().equals(user.getUsername()) &&
                    currentUser.getPassword().equals(user.getPassword())) {
                return currentUser;
            }
        }
        return null;
    }

}
