package ua.nure.usermanagement.gui;

import ua.nure.usermanagement.database.exception.DatabaseException;
import ua.nure.usermanagement.util.TextManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeletePanel extends JPanel implements ActionListener {
    private Long bufferedId;
    private MainFrame parentFrame;
    private JButton okButton;
    private JButton cancelButton;
    private JLabel warningLabel;
    private JPanel buttonsPanel;

    public DeletePanel(MainFrame mainFrame) {
        this.parentFrame = mainFrame;
        initialize();
    }

    private void initialize() {
        this.setName("deletePanel");
        this.setLayout(new BorderLayout());
        this.add(getWarningLabel(), BorderLayout.CENTER);
        this.add(getButtonsPanel(), BorderLayout.SOUTH);
    }

    private JButton getCancelButton() {
        if (cancelButton == null) {
            cancelButton = new JButton();
            cancelButton.setText(TextManager.getString("addPanel.cancel"));
            cancelButton.setActionCommand("cancel");
            cancelButton.setName("cancelButton");
            cancelButton.addActionListener(this);
        }
        return cancelButton;
    }

    private JButton getOkButton() {
        if (okButton == null) {
            okButton = new JButton();
            okButton.setText(TextManager.getString("deletePanel.delete"));
            okButton.setName("okButton");
            okButton.setActionCommand("ok");
            okButton.addActionListener(this);
        }
        return okButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if ("ok".equalsIgnoreCase(actionCommand)) {
            try {
                parentFrame.getUserDao().delete(parentFrame.getUserDao().find(bufferedId));
            } catch (DatabaseException e1) {
                JOptionPane.showMessageDialog(this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        setVisible(false);
        parentFrame.showBrowsePanel();
    }

    public void setBufferedId(Long id) {
        this.bufferedId = id;
    }

    public JLabel getWarningLabel() {
        if (warningLabel == null) {
            warningLabel = new JLabel();
            warningLabel.setText(TextManager.getString("deletePanel.warning"));
        }
        return warningLabel;
    }

    private JPanel getButtonsPanel() {
        if (buttonsPanel == null) {
            buttonsPanel = new JPanel();
            buttonsPanel.add(getOkButton());
            buttonsPanel.add(getCancelButton());
        }
        return buttonsPanel;
    }
}
