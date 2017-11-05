package ua.nure.usermanagement.gui;

import ua.nure.usermanagement.User;
import ua.nure.usermanagement.database.exception.DatabaseException;
import ua.nure.usermanagement.util.TextManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;

/**
 * A panel for detailed view of selected user. Prohibits modification of said user
 */
public class DetailsPanel extends JPanel implements ActionListener {
    private MainFrame parentFrame;
    private JPanel buttonsPanel;
    private JPanel fieldsPanel;
    private JButton backButton;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField dateOfBirthField;

    public DetailsPanel(MainFrame mainFrame) {
        this.parentFrame = mainFrame;
        initialize();
    }

    /**
     * A method, that adds a labeled field into a panel
     * @param panel A container for the labeled field
     * @param labelText Text, that is going to be inserted into a label
     * @param textField A textfield, that is labeled with {@code labelText}
     */
    private void addLabeledField(JPanel panel, String labelText, JTextField textField) {
        JLabel label = new JLabel(labelText);
        label.setLabelFor(textField);
        panel.add(label);
        panel.add(textField);
    }

    /**
     * Initializes interface of DetailsPanel object
     */
    private void initialize() {
        this.setName("detailsPanel");
        this.setLayout(new BorderLayout());
        this.add(getFieldsPanel(), BorderLayout.NORTH);
        this.add(getButtonsPanel(), BorderLayout.SOUTH);
    }

    private JPanel getButtonsPanel() {
        if (buttonsPanel == null) {
            buttonsPanel = new JPanel();
            buttonsPanel.add(getBackButton());
        }
        return buttonsPanel;
    }

    private JPanel getFieldsPanel() {
        if (fieldsPanel == null) {
            fieldsPanel = new JPanel();
            fieldsPanel.setLayout(new GridLayout(3, 2));
            addLabeledField(fieldsPanel, TextManager.getString("addPanel.first.name"), getFirstNameField());
            addLabeledField(fieldsPanel, TextManager.getString("addPanel.last.name"), getLastNameField());
            addLabeledField(fieldsPanel, TextManager.getString("addPanel.date.of.birth"), getDateOfBirthField());
        }
        return fieldsPanel;
    }

    private JTextField getFirstNameField() {
        if (firstNameField == null) {
            firstNameField = new JTextField();
            firstNameField.setEditable(false);
            firstNameField.setName("firstNameField");
        }
        return firstNameField;
    }

    private JTextField getLastNameField() {
        if (lastNameField == null) {
            lastNameField = new JTextField();
            lastNameField.setEditable(false);
            lastNameField.setName("lastNameField");
        }
        return lastNameField;
    }

    public JTextField getDateOfBirthField() {
        if (dateOfBirthField == null) {
            dateOfBirthField = new JTextField();
            dateOfBirthField.setEditable(false);
            dateOfBirthField.setName("dateOfBirthField");
        }
        return dateOfBirthField;
    }

    private JButton getBackButton() {
        if (backButton == null) {
            backButton = new JButton();
            backButton.setText(TextManager.getString("detailsPanel.back"));
            backButton.setName("backButton");
            backButton.setActionCommand("back");
            backButton.addActionListener(this);
        }
        return backButton;
    }

    /**
     * Implements functionality of buttons inside DetailsPanel
     *
     * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if ("back".equalsIgnoreCase(e.getActionCommand())) {
            setVisible(false);
            clearFields();
            parentFrame.showBrowsePanel();
        }
    }

    /**
     * Clears text fields of the object for future use
     */
    private void clearFields() {
        getFirstNameField().setText("");
        getLastNameField().setText("");
    }

    /**
     * Sets up user data for viewing
     * @param id An id of viewed user
     */
    public void showUser(Long id){
        try {
            User user = parentFrame.getUserDao().find(id);
            getFirstNameField().setText(user.getFirstName());
            getLastNameField().setText(user.getLastName());
            getDateOfBirthField().setText(DateFormat.getDateInstance().format(user.getDateOfBirth()));
        } catch (DatabaseException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            this.setVisible(false);
            parentFrame.showBrowsePanel();
        }
    }
}
