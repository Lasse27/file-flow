import utility.LoggingFormatter;
import view.MainWindow;

import javax.swing.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Main class of the application. Entry point.
 *
 * @author Lasse27
 * @since 2025-09-02
 */
public final class Main
{
	/**
	 * A logger instance used to log messages for the {@code Main} class.
	 */
	private static final Logger LOGGER = Logger.getLogger(Main.class.getName());



	static
	{
		setupLogging();
	}



	/**
	 * Private constructor to prevent instantiation of the Main class.
	 */
	private Main () {}



	/**
	 * The main entry point of the application. This method is executed to start the program.
	 *
	 * @param args Command-line arguments passed to the application.
	 */
	public static void main (final String[] args)
	{
		SwingUtilities.invokeLater(Main::launchMainWindow);
	}



	/**
	 * Sets up the logging system for the application.
	 */
	private static void setupLogging ()
	{
		final ConsoleHandler handler = new ConsoleHandler();
		handler.setFormatter(new LoggingFormatter());
		handler.setLevel(Level.ALL);
		LOGGER.setUseParentHandlers(false);
		LOGGER.addHandler(handler);
	}



	/**
	 * Launches the main window of the application.
	 */
	private static void launchMainWindow ()
	{
		final MainWindow mainWindow = new MainWindow();
		mainWindow.pack();
		mainWindow.setVisible(true);
		LOGGER.info("Application started.");
	}
}
