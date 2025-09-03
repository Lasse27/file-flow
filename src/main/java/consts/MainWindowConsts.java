package consts;

import javax.swing.*;

/**
 * Interface containing constants that provide
 * configuration settings for the main application window.
 */
public interface MainWindowConsts
{
    /**
     * Represents the title displayed in the title bar of the main application window.
     */
    String MAIN_WINDOW_TITLE = "FileFlow";

    /**
     * Defines the default close operation for the main application window.
     */
    int MAIN_WINDOW_CLOSE_DEFAULT = JFrame.EXIT_ON_CLOSE;


    /**
     * Determines whether the initial location of the main application window
     */
    boolean MAIN_WINDOW_LOCATION_BY_PLATFORM = true;

    /**
     * Determines whether the user can resize the main application window.
     */
    boolean MAIN_WINDOW_RESIZABLE = true;

    /**
     * Defines the default width of the main application window in pixels.
     */
    int MAIN_WINDOW_WIDTH = 800;

    /**
     * Defines the default height of the main application window in pixels.
     */
    int MAIN_WINDOW_HEIGHT = 800;

    /**
     * Defines the minimum width constraint for the main application window.
     */
    int MAIN_WINDOW_MIN_WIDTH = 640;

    /**
     * Specifies the minimum height for the main application window.
     */
    int MAIN_WINDOW_MIN_HEIGHT = 480;

    /**
     * Specifies the maximum width for the main application window.
     */
    int MAIN_WINDOW_MAX_WIDTH = 1920;

    /**
     * Specifies the maximum height for the main application window.
     */
    int MAIN_WINDOW_MAX_HEIGHT = 1080;

    int SPLIT_PANE_DIVIDER_LOCATION = MAIN_WINDOW_WIDTH / 2;
}
