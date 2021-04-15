package main.client.springaopaspects;

import main.common.entities.Category;
import main.common.entities.Expense;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

import javax.swing.*;

@Aspect
public class DialogueAspect {

    @AfterReturning(pointcut = "execution (* Client.registerNewCategory(..))", returning="cat")
    public void registerCategory(Category cat) {
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

    @AfterReturning(pointcut = "execution (* Client.registerNewExpense(..))", returning="expense")
    public void registerExpense(Expense expense) {
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
