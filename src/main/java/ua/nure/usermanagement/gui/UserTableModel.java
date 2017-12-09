package ua.nure.usermanagement.gui;

import ua.nure.usermanagement.User;
import ua.nure.usermanagement.util.TextManager;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collection;

/**
 * An implementation of AbstractTableModel for the user management database
 * @see javax.swing.table.AbstractTableModel
 */
public class UserTableModel extends AbstractTableModel {
    private static final String[] COLUMN_NAMES = {TextManager.getString("userTableModel.id"),
            TextManager.getString("userTableModel.first.name"),
            TextManager.getString("userTableModel.last.name")};
    private static final Class[] COLUMN_CLASSES = {Long.class, String.class, String.class};
    private ArrayList<User> users;

    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    public String getColumnName(int columnIndex) {
        return COLUMN_NAMES[columnIndex];
    }

    public Class getColumnClass(int columnIndex) {
        return COLUMN_CLASSES[columnIndex];
    }

    public UserTableModel(Collection<User> users) {
        this.users = new ArrayList<>(users);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        User user = users.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return user.getId();
            case 1:
                return user.getFirstName();
            case 2:
                return user.getLastName();
        }
        return null;
    }

    public void addUsers(Collection<User> users) {
        this.users.addAll(users);
    }

    public void clearUsers() {
        users = new ArrayList<>();
    }
}
