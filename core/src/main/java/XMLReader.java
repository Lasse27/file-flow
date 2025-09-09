import exception.XMLReaderException;
import lombok.Getter;
import lombok.ToString;
import model.XMLConfiguration;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;


/**
 * The XMLReader class encapsulates functionality for reading and parsing XML files. This class supports initializing an XML reader using either a file path or a {@code File}
 * object. It processes the specified XML file and provides a mechanism for handling XML-related operations.
 */
@Getter
@ToString
public class XMLReader
{

	/**
	 * Represents the XML file to be read and parsed. This file is specified during the construction of an {@code XMLReader} instance and serves as the primary source for XML
	 * data processing.
	 */
	private final File xmlFile;



	/**
	 * Constructs an XMLReader instance using the provided file path. The file path is converted into a File object, which is then used for reading and parsing XML data.
	 *
	 * @param filePath the path to the XML file to be read
	 */
	public XMLReader (final String filePath)
	{
		this(new File(filePath));
	}



	/**
	 * Constructs an XMLReader instance using the provided XML file. The supplied file will be used for reading and parsing XML data.
	 *
	 * @param xmlFile the XML file to be read and parsed
	 */
	public XMLReader (final File xmlFile)
	{
		this.xmlFile = xmlFile;
	}



	/**
	 * Reads and parses the XML file associated with this {@code XMLReader} instance.
	 *
	 * @return an {@code XMLConfiguration} object representing the configuration data in the XML file.
	 *
	 * @throws XMLReaderException if the XML file cannot be read or parsed
	 */
	public XMLConfiguration read ()
	{
		try
		{
			// Create a DocumentBuilder
			final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			final DocumentBuilder builder = factory.newDocumentBuilder();

			// Create a new Document
			final Document document = builder.parse(this.xmlFile);

		}
		catch (final IOException | ParserConfigurationException | SAXException exception)
		{
			throw new XMLReaderException(this.xmlFile, exception);
		}
		return null;
	}
}
