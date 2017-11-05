package ua.nure.usermanagement.gui;

import ua.nure.usermanagement.User;
import ua.nure.usermanagement.database.exception.DatabaseException;
import ua.nure.usermanagement.util.TextManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;

/**
 * Represents a panel, that implements GUI for adding a user to database
 */
public class AddPanel extends JPanel implements ActionListener {

    private MainFrame parentFrame;
    private JPanel buttonsPanel;
    private JPanel fieldsPanel;
    private JButton cancelButton;
    private JButton okButton;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField dateOfBirthField;

    AddPanel(MainFrame mainFrame) {
        this.parentFrame = mainFrame;
        initialize();
    }

    /**
     * Initializes interface of the AddPanel object.
     */
    private void initialize() {
        this.setName("addPanel");
        this.setLayout(new BorderLayout());
        this.add(getFieldsPanel(), BorderLayout.NORTH);
        this.add(getButtonsPanel(), BorderLayout.SOUTH);

    }

    /**
     * Implements functionality of buttons inside AddPanel
     *
     * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if ("ok".equalsIgnoreCase(actionCommand)) {
            try {
                User user = new User(getFirstNameField().getText(),
                        getLastNameField().getText(),
                        DateFormat.getDateInstance().parse(getDateOfBirthField().getText()));
                parentFrame.getUserDao().create(user);
            } catch (ParseException e1) {
                getDateOfBirthField().setBackground(Color.RED);
                return;
            } catch (DatabaseException e1) {
                JOptionPane.showMessageDialog(this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            this.clearFields();
            this.setVisible(false);
            parentFrame.showBrowsePanel();
        }
        if ("cancel".equalsIgnoreCase(actionCommand)) {
            this.clearFields();
            this.setVisible(false);
            parentFrame.showBrowsePanel();
        }
    }

    private JPanel getButtonsPanel() {
        if (buttonsPanel == null) {
            buttonsPanel = new JPanel();
            buttonsPanel.add(getOkButton());
            buttonsPanel.add(getCancelButton());
        }
        return buttonsPanel;
    }

    private JPanel getFieldsPanel() {
        if (fieldsPanel == null) {
            fieldsPanel = new JPanel(new GridLayout(3, 2));
            addLabeledField(fieldsPanel, TextManager.getString("addPanel.first.name"), getFirstNameField());
            addLabeledField(fieldsPanel, TextManager.getString("addPanel.last.name"), getLastNameField());
            addLabeledField(fieldsPanel, TextManager.getString("addPanel.date.of.birth"), getDateOfBirthField());

        }
        return fieldsPanel;
    }

    private JButton getCancelButton() {
        if (cancelButton == null) {
            cancelButton = new JButton();
            cancelButton.setText(TextManager.getString("addPanel.cancel"));
            cancelButton.setName("cancelButton");
            cancelButton.setActionCommand("cancel");
            cancelButton.addActionListener(this);
        }
        return cancelButton;
    }

    private JButton getOkButton() {
        if (okButton == null) {
            okButton = new JButton();
            okButton.setText(TextManager.getString("browsePanel.add"));
            okButton.setName("okButton");
            okButton.setActionCommand("ok");
            okButton.addActionListener(this);
        }
        return okButton;
    }

    /**
     * A method, that adds a labeled field into a panel
     *
     * @param panel     A container for the labeled field
     * @param labelText Text, that is going to be inserted into a label
     * @param textField A textfield, that is labeled with {@code labelText}
     */
    private void addLabeledField(JPanel panel, String labelText, JTextField textField) {
        JLabel label = new JLabel(labelText);
        label.setLabelFor(textField);
        panel.add(label);
        panel.add(textField);
    }

    private JTextField getFirstNameField() {
        if (firstNameField == null) {
            firstNameField = new JTextField();
            firstNameField.setName("firstNameField");
        }
        return firstNameField;
    }

    private JTextField getLastNameField() {
        if (lastNameField == null) {
            lastNameField = new JTextField();
            lastNameField.setName("lastNameField");
        }
        return lastNameField;
    }

    private JTextField getDateOfBirthField() {
        if (dateOfBirthField == null) {
            dateOfBirthField = new JTextField();
            dateOfBirthField.setName("dateOfBirthField");
        }
        return dateOfBirthField;
    }

    /**
     * Clears text fields of the object for future use
     */
    private void clearFields() {
        getFirstNameField().setText("");
        getLastNameField().setText("");
        getDateOfBirthField().setText("");
        getFirstNameField().setBackground(Color.white);
        getLastNameField().setBackground(Color.white);
        getDateOfBirthField().setBackground(Color.white);
    }

}
