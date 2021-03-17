package main.client.SwingApp;

import javax.swing.table.DefaultTableModel;

public class CustomTableModel extends DefaultTableModel {

    public CustomTableModel() {
        super();
    }

    public CustomTableModel(Object[][] data, Object[] columns) {
        super(data, columns);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
