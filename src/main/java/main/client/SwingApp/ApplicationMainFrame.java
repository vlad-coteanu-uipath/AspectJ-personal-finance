/*
 * Created by JFormDesigner on Mon Mar 08 17:39:51 EET 2021
 */

package main.client.SwingApp;

import main.client.ClientCache;

import javax.swing.*;
import java.awt.*;

/**
 * @author unknown
 */
public class ApplicationMainFrame extends JFrame {

    private CategoriesPage categoriesPageRef;
    private ExpensesPage expensesPageRef;

    public ApplicationMainFrame() {

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(750, 600));
        initComponents();

        categoriesPageRef = new CategoriesPage();
        expensesPageRef = new ExpensesPage();

        mainTabbedPane.addTab("Expenses", expensesPageRef);
        mainTabbedPane.addTab("Categories", categoriesPageRef);
        this.setVisible(true);
    }

    public void close() {
        this.dispose();
        Runtime.getRuntime().exit(0);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        mainTabbedPane = new JTabbedPane();

        //======== this ========
        setTitle("Personal Finance");
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(mainTabbedPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JTabbedPane mainTabbedPane;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

}
