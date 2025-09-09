package exception;

import java.io.File;

/**
 * Exception class representing an error that occurs during the writing process to an XML file.
 */
public class XMLWriterException extends RuntimeException
{

    /**
     * Constant string representing a default error message used when an XML file cannot be written.
     */
    private static final String MESSAGE = "Could not write the XML file.";


    /**
     * Constructs a new XMLWriterException with a detailed error message and the original exception that caused this error.
     *
     * @param file the {@link File} object representing the XML file being written when the error occurred.
     * @param e    the original exception that caused this error (e.g., IOException, SecurityException, etc.).
     */
    public XMLWriterException(final File file, final Exception e)
    {
        super(MESSAGE + "#" + file.getAbsolutePath(), e);
    }


    /**
     * Constructs a new XMLWriterException with a detailed error message and the original exception that caused this error.
     *
     * @param filePath the path to the XML file being written when the error occurred.
     * @param e        the original exception that caused this error (e.g., IOException, SecurityException, etc.).
     */
    public XMLWriterException(final String filePath, final Exception e)
    {
        super(MESSAGE + "#" + filePath, e);
    }
}
