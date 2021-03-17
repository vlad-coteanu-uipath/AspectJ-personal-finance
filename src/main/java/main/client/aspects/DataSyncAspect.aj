package main.client.aspects;

import com.diogonunes.jcolor.Attribute;
import main.client.Client;
import main.client.SwingApp.ExpensesPage;
import main.client.SwingApp.CategoriesPage;
import main.common.entities.User;

import static com.diogonunes.jcolor.Ansi.colorize;

public aspect DataSyncAspect {

    private CategoriesPage _categoriesPage;
    private ExpensesPage _expensesPage;

    pointcut registerCategory() : call (* Client.registerNewCategory(..));
    pointcut registerExpense() : call (* Client.registerNewExpense(..));
    pointcut categoriesPageCreation() : call(CategoriesPage.new(..));
    pointcut expensesPageCreation() : call(ExpensesPage.new(..));

    after() returning (CategoriesPage categoriesPage): categoriesPageCreation() {
        System.out.println(colorize("Storing categories page", Attribute.GREEN_TEXT()));
        _categoriesPage = categoriesPage;
        _categoriesPage.syncCategories();
    }

    after() returning (ExpensesPage expensesPage): expensesPageCreation() {
        System.out.println(colorize("Storing expenses page", Attribute.GREEN_TEXT()));
        _expensesPage = expensesPage;
        _expensesPage.syncCategories();
        _expensesPage.syncExpenses();
    }

    after() returning : registerCategory() {
        if(_categoriesPage != null) {
            _categoriesPage.syncCategories();
            System.out.println(colorize("Sync-ing categories on Categories Page", Attribute.GREEN_TEXT()));
        } else {
            System.out.println(colorize("Warning, Categories Page is null", Attribute.YELLOW_TEXT()));
        }

        if(_expensesPage != null) {
            _expensesPage.syncCategories();
            System.out.println(colorize("Sync-ing categories on Expenses Page", Attribute.GREEN_TEXT()));
        } else {
            System.out.println(colorize("Warning, Expenses Page is null", Attribute.YELLOW_TEXT()));
        }
    }

    after() returning : registerExpense() {
        if(_expensesPage != null) {
            _expensesPage.syncExpenses();
            System.out.println(colorize("Sync-ing expenses on Expenses Page", Attribute.GREEN_TEXT()));
        } else {
            System.out.println(colorize("Warning, Expenses Page is null", Attribute.YELLOW_TEXT()));
        }
    }

}
