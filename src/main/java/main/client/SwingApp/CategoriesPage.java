/*
 * Created by JFormDesigner on Mon Mar 08 17:49:28 EET 2021
 */

package main.client.SwingApp;

import main.client.Client;
import main.client.ClientCache;
import main.common.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 * @author unknown
 */

@Component
public class CategoriesPage extends JPanel {

    @Autowired
    private Client client;

    private List<Category> categories = new ArrayList();

    public CategoriesPage() {
        super();
        initComponents();
        initCustomComponents();
    }

    private void initCustomComponents() {
        this.setSize(700, 550);

        registerCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String categoryName = categoryNameFeld.getText();
                if(categoryName != null && !categoryName.equals("")) {
                    Category newCat = new Category(0, categoryName, ClientCache.getInstance().getLoggedUser().getId());
                    client.registerNewCategory(newCat);
                    categoryNameFeld.setText("");
                } else {
                    JOptionPane.showMessageDialog(CategoriesPage.this,
                            "Category can't be empty",
                            "Register category error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }

    public void syncCategories() {
        if(ClientCache.getInstance().getLoggedUser() != null) {
            categories = client.getAllCategories();
            categoriesList.setListData(categories.toArray());
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
        categoryNameFeld = new JTextField();
        label5 = new JLabel();
        registerCategory = new JButton();
        panel1 = new JPanel();
        scrollPane1 = new JScrollPane();
        categoriesList = new JList();
        panel3 = new JPanel();
        label7 = new JLabel();
        label8 = new JLabel();

        //======== this ========
        setPreferredSize(new Dimension(613, 429));
        setMinimumSize(new Dimension(600, 400));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        //======== splitPane1 ========
        {
            splitPane1.setResizeWeight(0.4);

            //======== panel2 ========
            {
                panel2.setLayout(new GridLayout(10, 0));

                //---- label2 ----
                label2.setText("Register new categories");
                label2.setFont(label2.getFont().deriveFont(label2.getFont().getSize() + 3f));
                panel2.add(label2);
                panel2.add(label3);

                //---- label4 ----
                label4.setText("Category name:");
                panel2.add(label4);
                panel2.add(categoryNameFeld);
                panel2.add(label5);

                //---- registerCategory ----
                registerCategory.setText("Register category");
                panel2.add(registerCategory);
            }
            splitPane1.setLeftComponent(panel2);

            //======== panel1 ========
            {
                panel1.setLayout(new BorderLayout());

                //======== scrollPane1 ========
                {
                    scrollPane1.setViewportView(categoriesList);
                }
                panel1.add(scrollPane1, BorderLayout.CENTER);

                //======== panel3 ========
                {
                    panel3.setLayout(new GridLayout(3, 0));
                    panel3.add(label7);

                    //---- label8 ----
                    label8.setText("Registered categories:");
                    panel3.add(label8);
                }
                panel1.add(panel3, BorderLayout.NORTH);
            }
            splitPane1.setRightComponent(panel1);
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
    private JTextField categoryNameFeld;
    private JLabel label5;
    private JButton registerCategory;
    private JPanel panel1;
    private JScrollPane scrollPane1;
    private JList categoriesList;
    private JPanel panel3;
    private JLabel label7;
    private JLabel label8;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
