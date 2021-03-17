package main.server.aspects;

import com.diogonunes.jcolor.Attribute;
import main.common.entities.Category;
import main.common.entities.Expense;
import main.common.entities.User;
import main.server.BinFileDatabaseRepositoryImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.diogonunes.jcolor.Ansi.colorize;
import static main.server.BinFileDatabaseRepositoryImpl.*;

public aspect DbIntegrityAspect {

    pointcut createDbInstance() : call(BinFileDatabaseRepositoryImpl.new(..));

    after() returning : createDbInstance() {

        System.out.println(colorize("Checking database integrity", Attribute.BLUE_TEXT()));

        File usersFile = new File(USERS_FILE_NAME);
        if(!usersFile.exists()) {
            System.out.println(colorize("   Creating users file", Attribute.BLUE_TEXT()));
            try (ObjectOutputStream ois = new ObjectOutputStream(new FileOutputStream(usersFile))) {
                User u1 = new User(0, "vlad.coteanu", "parola");
                User u2 = new User(1, "sebastian.coteanu", "parola");
                List<User> userList = new ArrayList<User>();
                userList.add(u1);
                userList.add(u2);
                ois.writeObject(userList);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(colorize("   Users file created", Attribute.BLUE_TEXT()));
        }

        File categoriesFile = new File(CATEGORIES_FILE_NAME);
        if(!categoriesFile.exists()) {
            System.out.println(colorize("   Creating categories file", Attribute.BLUE_TEXT()));
            try (ObjectOutputStream ois = new ObjectOutputStream(new FileOutputStream(categoriesFile))) {
                ois.writeObject(new ArrayList<Category>());
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(colorize("   Categories file created", Attribute.BLUE_TEXT()));
        }

        File expensesFile = new File(EXPENSES_FILE_NAME);
        if(!expensesFile.exists()) {
            System.out.println(colorize("   Creating expenses file", Attribute.BLUE_TEXT()));
            try (ObjectOutputStream ois = new ObjectOutputStream(new FileOutputStream(expensesFile))) {
                ois.writeObject(new ArrayList<Expense>());
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(colorize("   Expenses file created", Attribute.BLUE_TEXT()));
        }

        System.out.println(colorize("Integrity check ended", Attribute.BLUE_TEXT()));

    }

}
