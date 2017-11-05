package ua.nure.usermanagement.gui;

import ua.nure.usermanagement.database.DaoFactoryImpl;
import ua.nure.usermanagement.database.UserDao;
import ua.nure.usermanagement.util.TextManager;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private static final int FRAME_WIDTH = 600;
    private static final int FRAME_HEIGHT = 400;
    private JPanel contentPanel;
    private JPanel browsePanel;
    private JPanel addPanel;
    private JPanel editPanel;
    private JPanel detailsPanel;

    private UserDao userDao;
    private JPanel deletePanel;

    public MainFrame() {
        super();
        userDao = DaoFactoryImpl.getInstance().getUserDao();
        initialize();
    }


    private void initialize() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setTitle(TextManager.getString("mainFrame.title"));
        this.setContentPane(getContentPanel());
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(getBrowsePanel(), BorderLayout.CENTER);

    }

    private JPanel getContentPanel() {
        if (contentPanel == null) {
            contentPanel = new JPanel();
        }
        return contentPanel;
    }

    private JPanel getBrowsePanel() {
        if (browsePanel == null) {
            browsePanel = new BrowsePanel(this);
        }
        ((BrowsePanel) browsePanel).initTable();
        return browsePanel;
    }

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }

    public void showAddPanel() {
        showPanel(getAddPanel());

    }

    private void showPanel(JPanel panel) {
        this.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setVisible(true);
        panel.repaint();
    }

    public JPanel getAddPanel() {
        if (addPanel == null) {
            addPanel = new AddPanel(this);
        }
        return addPanel;
    }

    public void showEditPanel(Long id) {
        showPanel(getEditPanel(id));
    }

    public JPanel getEditPanel(Long id) {
        if (editPanel == null) {
            editPanel = new EditPanel(this);
        }
        ((EditPanel) editPanel).showUser(id);
        return editPanel;
    }

    public void showDetailsPanel(Long id) {
        showPanel(getDetailsPanel(id));
    }

    public JPanel getDetailsPanel(Long id) {
        if (detailsPanel == null) {
            detailsPanel = new DetailsPanel(this);
        }
        ((DetailsPanel) detailsPanel).showUser(id);
        return detailsPanel;
    }

    public void showBrowsePanel() {
        showPanel(getBrowsePanel());
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void showDeletePanel(Long id) {
        showPanel(getDeletePanel(id));
    }

    private JPanel getDeletePanel(Long id) {
        if(deletePanel == null) {
            deletePanel = new DeletePanel(this);
        }
        ((DeletePanel) deletePanel).setBufferedId(id);
        return deletePanel;
    }
}
