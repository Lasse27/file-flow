package view;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;
import java.awt.*;

import static consts.MainWindowConsts.*;


/**
 * The MainWindow class serves as the main graphical user interface (GUI) container for the application. Corresponding .form file: MainWindow.form.
 *
 * @author Lasse27
 * @since 2025-09-02
 */
public final class MainWindow extends JFrame
{
    private JPanel basePanel;

    private JTabbedPane baseTabPaneControl;

    private JPanel sortDirsTabPanel;

    private JTree tree1;

    private JTextField fileFlowNameTextBox;

    private JTextField fileFlowDescrTextBox;

    private JLabel fileFlowDescrLabel;

    private JLabel fileFlowNameLabel;

    private JButton loadFromFileButton;

    private JTable table1;

    private JSplitPane flowConfigSplitPane;


    public MainWindow()
    {
        this.initializeComponents();
    }


    private void initializeComponents()
    {
        this.setTitle(MAIN_WINDOW_TITLE);
        this.setDefaultCloseOperation(MAIN_WINDOW_CLOSE_DEFAULT);
        this.setLocationByPlatform(MAIN_WINDOW_LOCATION_BY_PLATFORM);

        // Sizing
        this.setResizable(MAIN_WINDOW_RESIZABLE);
        this.setSize(MAIN_WINDOW_WIDTH, MAIN_WINDOW_HEIGHT);
        this.setMinimumSize(new Dimension(MAIN_WINDOW_MIN_WIDTH, MAIN_WINDOW_MIN_HEIGHT));
        this.setMaximumSize(new Dimension(MAIN_WINDOW_MAX_WIDTH, MAIN_WINDOW_MAX_HEIGHT));

        // SplitPane
        assert this.flowConfigSplitPane != null;
        this.flowConfigSplitPane.setDividerLocation(SPLIT_PANE_DIVIDER_LOCATION);
        this.setContentPane(this.basePanel);                    // Root-Panel aus dem Form-Designer verwenden
    }


    @Override
    public String toString()
    {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return this.getClass().getSimpleName() + this.hashCode() + gson.toJson(this);
    }
}
