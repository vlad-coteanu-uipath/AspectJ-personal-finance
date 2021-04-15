package main.server.repository;

import main.common.entities.Category;
import main.common.entities.Expense;
import main.common.entities.User;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
* Serialize and deserialize as List<Object>
* */

@Repository
public class BinFileDatabaseRepositoryImpl implements DatabaseRepository {

    public static final String USERS_FILE_NAME = "users_file_db";
    public static final String CATEGORIES_FILE_NAME = "categories_file_db";
    public static final String EXPENSES_FILE_NAME = "expenses_file_db";

    public boolean isDBReady() {
        File usersFile = new File(USERS_FILE_NAME);
        if(!usersFile.exists()) {
            return false;
        }

        File categoriesFile = new File(CATEGORIES_FILE_NAME);
        if(!categoriesFile.exists()) {
            return false;
        }

        File expensesFile = new File(EXPENSES_FILE_NAME);
        if(!expensesFile.exists()) {
            return false;
        }

        return true;
    }

    @Override
    public Category registerCategory(Category category) {
        List<Category> categories = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CATEGORIES_FILE_NAME))) {
            categories = (List<Category>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        category.setId(categories.size());
        categories.add(category);

        try (ObjectOutputStream ois = new ObjectOutputStream(new FileOutputStream(CATEGORIES_FILE_NAME))) {
            ois.writeObject(categories);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return category;
    }

    @Override
    public List<Category> getAllCategories(int userId) {
        List<Category> categories = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CATEGORIES_FILE_NAME))) {
            categories = (List<Category>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return categories.stream().filter(p->p.getUserId() == userId).collect(Collectors.toList());
    }

    @Override
    public Expense registerExpense(Expense expense) {
        List<Expense> expenses = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(EXPENSES_FILE_NAME))) {
            expenses = (List<Expense>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        expense.setId(expenses.size());
        expenses.add(expense);

        try (ObjectOutputStream ois = new ObjectOutputStream(new FileOutputStream(EXPENSES_FILE_NAME))) {
            ois.writeObject(expenses);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return expense;
    }

    @Override
    public List<Expense> getAllExpenses(int userId) {
        List<Expense> expenses = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(EXPENSES_FILE_NAME))) {
            expenses = (List<Expense>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return expenses.stream().filter(p->p.getUserId() == userId).collect(Collectors.toList());
    }

    @Override
    public User loginUser(User user) {
        List<User> users = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USERS_FILE_NAME))) {
            users = (List<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < users.size(); i ++) {
            if(users.get(i).getUsername().equals(user.getUsername()) &&
                    users.get(i).getPassword().equals(user.getPassword())) {
                return users.get(i);
            }
        }
        return null;
    }
}
