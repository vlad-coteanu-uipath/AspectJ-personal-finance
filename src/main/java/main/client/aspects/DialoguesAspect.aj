package main.client.aspects;

import main.client.SwingApp.CategoriesPage;
import main.client.SwingApp.ExpensesPage;
import main.common.entities.Category;
import main.client.Client;
import main.common.entities.Expense;

import javax.swing.*;

public aspect DialoguesAspect {

    pointcut registerCategory() : call (* Client.registerNewCategory(..));
    pointcut registerExpense() : call (* Client.registerNewExpense(..));

    after () returning (Category cat) : registerCategory() {
        if(cat == null) {
            JOptionPane.showMessageDialog(null,
                    "Something went wrong. Try again!",
                    "Register category error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null,
                    "Category created!",
                    "Category created",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    after () returning (Expense expense) : registerExpense() {
        if(expense == null) {
            JOptionPane.showMessageDialog(null,
                    "Something went wrong. Try again!",
                    "Register expense error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null,
                    "Expense created!",
                    "Expense created",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

}
