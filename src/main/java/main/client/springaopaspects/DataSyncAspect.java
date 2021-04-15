package main.client.springaopaspects;

import com.diogonunes.jcolor.Attribute;
import main.client.SwingApp.CategoriesPage;
import main.client.SwingApp.ExpensesPage;
import main.common.entities.Category;
import main.common.entities.Expense;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;

import static com.diogonunes.jcolor.Ansi.colorize;

@Component
@Aspect
public class DataSyncAspect {

    @Autowired
    private CategoriesPage _categoriesPage;

    @Autowired
    private ExpensesPage _expensesPage;

    @AfterReturning(pointcut = "execution (* *..*.Client.registerNewCategory(..))")
    public void registerCategory() {
        if(_categoriesPage != null) {
            _categoriesPage.syncCategories();
            System.out.println(colorize("Sync-ing categories on Categories Page", Attribute.MAGENTA_TEXT()));
        } else {
            System.out.println(colorize("Warning, Categories Page is null", Attribute.YELLOW_TEXT()));
        }

        if(_expensesPage != null) {
            _expensesPage.syncCategories();
            System.out.println(colorize("Sync-ing categories on Expenses Page", Attribute.MAGENTA_TEXT()));
        } else {
            System.out.println(colorize("Warning, Expenses Page is null", Attribute.YELLOW_TEXT()));
        }
    }

    @AfterReturning(pointcut = "execution (* *..*.Client.registerNewExpense(..))")
    public void registerExpense() {
        if(_expensesPage != null) {
            _expensesPage.syncExpenses();
            System.out.println(colorize("Sync-ing expenses on Expenses Page", Attribute.MAGENTA_TEXT()));
        } else {
            System.out.println(colorize("Warning, Expenses Page is null", Attribute.YELLOW_TEXT()));
        }
    }

}
