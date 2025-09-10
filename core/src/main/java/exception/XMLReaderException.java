package exception;


import java.io.File;


public class XMLReaderException extends RuntimeException
{

    private static final String message = "Could not read the XML file.";


    public XMLReaderException(final File file, final Exception e)
    {
        super(message + "#" + file.getAbsolutePath(), e);
    }


    public XMLReaderException(final String filePath, final Exception e)
    {
        super(message + "#" + filePath, e);
    }
}
