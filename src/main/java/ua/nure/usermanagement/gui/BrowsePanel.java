package ua.nure.usermanagement.gui;

import ua.nure.usermanagement.database.exception.DatabaseException;
import ua.nure.usermanagement.util.TextManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/**
 * Represents a panel, that implements GUI for browsing users database
 */
public class BrowsePanel extends JPanel implements ActionListener {

    private MainFrame parentFrame;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton detailsButton;
    private JPanel buttonsPanel;
    private JScrollPane tablePanel;
    private JTable userTable;


    BrowsePanel(MainFrame mainFrame) {
        this.parentFrame = mainFrame;
        initialize();
    }

    /**
     * Initializes UI of the panel
     */
    private void initialize() {
        this.setName("browsePanel");
        this.setLayout(new BorderLayout());
        this.add(getTablePanel(), BorderLayout.CENTER);
        this.add(getButtonsPanel(), BorderLayout.SOUTH);
    }

    private JScrollPane getTablePanel() {
        if (tablePanel == null) {
            tablePanel = new JScrollPane(getUserTable());
        }
        return tablePanel;
    }

    private JPanel getButtonsPanel() {
        if (buttonsPanel == null) {
            buttonsPanel = new JPanel();
            buttonsPanel.add(getAddButton());
            buttonsPanel.add(getEditButton());
            buttonsPanel.add(getDeleteButton());
            buttonsPanel.add(getDetailsButton());
        }
        return buttonsPanel;
    }

    private JButton getAddButton() {
        if (addButton == null) {
            addButton = new JButton();
            addButton.setText(TextManager.getString("browsePanel.add"));
            addButton.setName("addButton");
            addButton.setActionCommand("add");
            addButton.addActionListener(this);
        }
        return addButton;
    }

    private JButton getEditButton() {
        if (editButton == null) {
            editButton = new JButton();
            editButton.setText(TextManager.getString("browsePanel.edit"));
            editButton.setName("editButton");
            editButton.setActionCommand("edit");
            editButton.addActionListener(this);
        }
        return editButton;
    }

    private JButton getDeleteButton() {
        if (deleteButton == null) {
            deleteButton = new JButton();
            deleteButton.setText(TextManager.getString("browsePanel.delete"));
            deleteButton.setName("deleteButton");
            deleteButton.setActionCommand("delete");
            deleteButton.addActionListener(this);
        }
        return deleteButton;
    }

    private JButton getDetailsButton() {
        if (detailsButton == null) {
            detailsButton = new JButton();
            detailsButton.setText(TextManager.getString("browsePanel.details"));
            detailsButton.setName("detailsButton");
            detailsButton.setActionCommand("details");
            detailsButton.addActionListener(this);
        }
        return detailsButton;
    }

    private JTable getUserTable() {
        if (userTable == null) {
            userTable = new JTable();
            userTable.setName("userTable");
        }
        return userTable;
    }

    void initTable() {
        try {
            userTable.setModel(new UserTableModel(parentFrame.getUserDao().findAll()));
        } catch (DatabaseException e) {
            userTable.setModel(new UserTableModel(new ArrayList<>()));
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Implements functionality of buttons inside BrowsePanel
     *
     * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if ("add".equalsIgnoreCase(actionCommand)) {
            this.setVisible(false);
            parentFrame.showAddPanel();
        }
        if ("edit".equalsIgnoreCase(actionCommand)) {
            if (userTable.getSelectedRow() != -1) {
                Long selectedUserId = (Long) userTable.getValueAt(userTable.getSelectedRow(), 0);
                this.setVisible(false);
                parentFrame.showEditPanel(selectedUserId);
            }
        }
        if ("delete".equalsIgnoreCase(actionCommand)) {
            if (userTable.getSelectedRow() != -1) {
                this.setVisible(false);
                parentFrame.showDeletePanel(((Long) userTable.getValueAt(userTable.getSelectedRow(), 0)));
            }
        }
        if ("details".equalsIgnoreCase(actionCommand)) {
            if (userTable.getSelectedRow() != -1) {
                Long selectedUserId = (Long) userTable.getValueAt(userTable.getSelectedRow(), 0);
                this.setVisible(false);
                parentFrame.showDetailsPanel(selectedUserId);
            }
        }
    }
}
