/*
 * Created by JFormDesigner on Mon Mar 08 17:47:46 EET 2021
 */

package main.client.SwingApp;

import main.client.Client;
import main.client.ClientCache;
import main.common.entities.Category;
import main.common.entities.Expense;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;

/**
 * @author unknown
 */
public class ExpensesPage extends JPanel {

    List<Expense> expenses;
    List<Category> categories;

    public ExpensesPage() {
        initComponents();
        this.setSize(700, 550);
        initializeCustomComponents();
    }

    void initializeCustomComponents() {
        categoryCombo.setModel(new CategoriesComboboxModel(categories, categoryCombo));

        registerExpense.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String expenseName = expenseNameField.getText();
                    int expenseValue = Integer.parseInt(expenseValueField.getText());
                    Category category = (Category) categoryCombo.getSelectedItem();
                    if(expenseName != null && ! expenseName.equals("") && category != null) {
                        int userId = ClientCache.getInstance().getLoggedUser().getId();
                        Expense expense = new Expense(0, expenseName, category.getId(), userId, expenseValue);
                        Client.getInstance().registerNewExpense(expense);
                        expenseNameField.setText("");
                        expenseValueField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(ExpensesPage.this,
                                "Expense name can't be empty. Expense value must be an integer.",
                                "Register category error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(ExpensesPage.this,
                            "Something went wrong. Try again!",
                            "Register category error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void loadData() {
        if(ClientCache.getInstance().getLoggedUser() != null) {
            syncCategories();
            syncExpenses();
        }
    }

    public void syncExpenses() {
        expenses = Client.getInstance().getAllExpenses();
        String[][] expenseData = new String[expenses.size()][4];
        for(int i = 0; i < expenses.size(); i++) {
            expenseData[i][0] = expenses.get(i).getName();
            expenseData[i][1] = String.valueOf(expenses.get(i).getValue());
            int categoryId = expenses.get(i).getCategoryId();
            Optional<String> categoryName = categories.stream().filter(p -> p.getId() == categoryId).map(p -> p.getName()).findFirst();
            expenseData[i][2] = categoryName.orElse("Unkown category");

        }
        String[] columns = {"DESCRIPTION","VALUE","CATEGORY"};
        expenseTable.setModel(new CustomTableModel(expenseData, columns));

        this.repaint();
    }

    public void syncCategories() {
        if(ClientCache.getInstance().getLoggedUser() != null) {
            categories = Client.getInstance().getAllCategories();
            categoryCombo.setModel(new CategoriesComboboxModel(categories, categoryCombo));
            this.repaint();
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        splitPane1 = new JSplitPane();
        panel2 = new JPanel();
        label2 = new JLabel();
        label3 = new JLabel();
        label4 = new JLabel();
        expenseNameField = new JTextField();
        label6 = new JLabel();
        expenseValueField = new JTextField();
        label5 = new JLabel();
        categoryCombo = new JComboBox();
        label7 = new JLabel();
        registerExpense = new JButton();
        scrollPane1 = new JScrollPane();
        expenseTable = new JTable();

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        //======== splitPane1 ========
        {
            splitPane1.setResizeWeight(0.4);

            //======== panel2 ========
            {
                panel2.setLayout(new GridLayout(11, 0));

                //---- label2 ----
                label2.setText("Register new expenses");
                label2.setFont(label2.getFont().deriveFont(label2.getFont().getSize() + 3f));
                panel2.add(label2);
                panel2.add(label3);

                //---- label4 ----
                label4.setText("Expense name");
                panel2.add(label4);
                panel2.add(expenseNameField);

                //---- label6 ----
                label6.setText("Expense value");
                panel2.add(label6);
                panel2.add(expenseValueField);

                //---- label5 ----
                label5.setText("Category");
                panel2.add(label5);
                panel2.add(categoryCombo);
                panel2.add(label7);

                //---- registerExpense ----
                registerExpense.setText("Register expense");
                panel2.add(registerExpense);
            }
            splitPane1.setLeftComponent(panel2);

            //======== scrollPane1 ========
            {
                scrollPane1.setViewportView(expenseTable);
            }
            splitPane1.setRightComponent(scrollPane1);
        }
        add(splitPane1);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JSplitPane splitPane1;
    private JPanel panel2;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JTextField expenseNameField;
    private JLabel label6;
    private JTextField expenseValueField;
    private JLabel label5;
    private JComboBox categoryCombo;
    private JLabel label7;
    private JButton registerExpense;
    private JScrollPane scrollPane1;
    private JTable expenseTable;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public JScrollPane getScrollPane1() {
        return scrollPane1;
    }

    public void setScrollPane1(JScrollPane scrollPane1) {
        this.scrollPane1 = scrollPane1;
    }
}
