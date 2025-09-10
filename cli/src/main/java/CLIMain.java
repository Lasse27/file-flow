import model.XMLConfiguration;
import model.XMLConfigurationMeta;
import xml.XMLWriter;


/**
 * The CLIMain class serves as the starting point of the application. It contains the main method which is the entry point for execution. This class cannot be instantiated.
 */
public final class CLIMain
{

	/**
	 * Private constructor to prevent instantiation of the CLIMain class. The CLIMain class is a utility class containing the main entry point and should not be instantiated.
	 */
	private CLIMain () {}



	/**
	 * The entry point of the application.
	 *
	 * @param args command-line arguments passed to the application.
	 */
	public static void main (final String[] args)
	{
		final XMLConfigurationMeta metadata = new XMLConfigurationMeta("Lasse", "1.0.0", "Descr", "lasste", "10.01.1010");
		final XMLConfiguration configuration = new XMLConfiguration(metadata);
		final XMLWriter writer = new XMLWriter("C:/Users/Lasse/Desktop/output.xml");
		writer.write(configuration);
	}
}
