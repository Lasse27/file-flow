package control.xml;


import exception.XMLWriterException;
import lombok.Getter;
import lombok.ToString;
import model.xml.XMLConfiguration;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Utility class for writing data into an XML file. This class integrates with the {@link XMLConfiguration}
 * object to build and persist XML data structures.
 */
@Getter
@ToString
public class XMLWriter
{

    /**
     * Represents the XML file to which the data will be written.
     */
    private final File xmlFile;


    /**
     * Constructs an control.xml.XMLWriter instance using the path to an XML file.
     *
     * @param filePath the path to the XML file to be written
     */
    public XMLWriter(final String filePath)
    {
        this(new File(filePath));
    }


    /**
     * Constructs an control.xml.XMLWriter instance using the provided XML file.
     *
     * @param xmlFile the XML file to which data will be written
     */
    public XMLWriter(final File xmlFile)
    {
        this.xmlFile = xmlFile;
    }


    /**
     * Writes the content of the provided XMLConfiguration object to an XML file.
     *
     * @param configuration the {@link XMLConfiguration} object containing the data to be written to the XML file
     * @throws XMLWriterException if there is an error during the XML writing process
     */
    public void write(final XMLConfiguration configuration)
    {
        try
        {
            // Create a DocumentBuilder
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document document = builder.newDocument();

            // Build document
            final XMLBuilder xmlBuilder = new XMLBuilder(document);
            document.appendChild(xmlBuilder.buildAll(configuration));

            // TODO: Write run-options

            // TODO: Write steps

            // TODO: Finish exec
            this.writeDOMToXML(document);
        }
        catch (final ParserConfigurationException | TransformerException | FileNotFoundException exception)
        {
            throw new XMLWriterException(this.xmlFile, exception);
        }
    }





    /**
     * Writes the content of the given {@link Document} object to an XML file specified by the {@code xmlFile} field.
     *
     * @param document the {@link Document} object representing the XML DOM structure to be written to the file
     * @throws FileNotFoundException if the specified file cannot be created or opened for writing
     * @throws TransformerException  if an error occurs during the transformation process
     */
    private void writeDOMToXML(final Document document) throws FileNotFoundException, TransformerException
    {
        final TransformerFactory transformerFactory = TransformerFactory.newInstance();
        final Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        final Source source = new DOMSource(document);
        final Result result = new StreamResult(new FileOutputStream(this.xmlFile));
        transformer.transform(source, result);
    }
}
