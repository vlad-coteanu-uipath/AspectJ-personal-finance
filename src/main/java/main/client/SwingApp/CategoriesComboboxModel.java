package main.client.SwingApp;

import main.common.entities.Category;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.util.List;

public class CategoriesComboboxModel implements ComboBoxModel {

    private List<Category> categories;
    private Category selectedCategory;
    private JComboBox jComboBox;

    public CategoriesComboboxModel(List<Category> categories, JComboBox ownerCombobox) {
        this.categories = categories;
        this.jComboBox = ownerCombobox;
        jComboBox.revalidate();
        jComboBox.repaint();
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
        jComboBox.revalidate();
        jComboBox.repaint();
    }

    @Override
    public void setSelectedItem(Object anItem) {
        this.selectedCategory = (Category) anItem;
    }

    @Override
    public Object getSelectedItem() {
        return selectedCategory;
    }

    @Override
    public int getSize() {
        return categories.size();
    }

    @Override
    public Object getElementAt(int index) {
        return categories.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {

    }

    @Override
    public void removeListDataListener(ListDataListener l) {

    }
}
