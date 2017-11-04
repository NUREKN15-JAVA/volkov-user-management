package ua.nure.usermanagement.gui;

import ua.nure.usermanagement.util.TextManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DetailsPanel extends JPanel implements ActionListener {
    private MainFrame parentFrame;
    private JPanel buttonsPanel;
    private JPanel fieldsPanel;
    private JButton backButton;
    private JTextField firstNameField;
    private JTextField lastNameField;

    public DetailsPanel(MainFrame mainFrame, String firstName, String lastName) {
        this.parentFrame = mainFrame;
        initialize(firstName,lastName);
    }

    private void addLabeledField(JPanel panel, String labelText, JTextField textField) {
        JLabel label = new JLabel(labelText);
        label.setLabelFor(textField);
        panel.add(label);
        panel.add(textField);
    }

    private void initialize(String firstName, String lastName) {
        this.setName("detailsPanel");
        this.setLayout(new BorderLayout());
        this.add(getFieldsPanel(), BorderLayout.NORTH);
        this.add(getButtonsPanel(), BorderLayout.SOUTH);
        getFirstNameField().setText(firstName);
        getLastNameField().setText(lastName);
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
            fieldsPanel.setLayout(new GridLayout(2, 2));
            addLabeledField(fieldsPanel, TextManager.getString("addPanel.first.name"), getFirstNameField());
            addLabeledField(fieldsPanel, TextManager.getString("addPanel.last.name"), getLastNameField());
        }
        return fieldsPanel;
    }

    public JTextField getFirstNameField() {
        if (firstNameField == null) {
            firstNameField = new JTextField();
            firstNameField.setEditable(false);
            firstNameField.setName("firstNameField");
        }
        return firstNameField;
    }

    public JTextField getLastNameField() {
        if (lastNameField == null) {
            lastNameField = new JTextField();
            lastNameField.setEditable(false);
            lastNameField.setName("lastNameField");
        }
        return lastNameField;
    }

    public JButton getBackButton() {
        if (backButton == null) {
            backButton = new JButton();
            backButton.setText(TextManager.getString("detailsPanel.back"));
            backButton.setName("backButton");
            backButton.setActionCommand("back");
            backButton.addActionListener(this);
        }
        return backButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("back".equalsIgnoreCase(e.getActionCommand())) {
            clearFields();
            parentFrame.showBrowsePanel();
        }
    }

    private void clearFields() {
        getFirstNameField().setText("");
        getLastNameField().setText("");
    }
}
