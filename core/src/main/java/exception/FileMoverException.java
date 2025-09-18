package exception;


/**
 * An exception that represents errors occurring during file movement operations.
 * <p>
 * This exception serves as a custom runtime exception specifically for issues related to the process of moving files.
 */
public final class FileMoverException extends RuntimeException
{
    /**
     * {@inheritDoc}
     */
    public FileMoverException()
    {
    }


    /**
     * {@inheritDoc}
     */
    public FileMoverException(final String message)
    {
        super(message);
    }


    /**
     * {@inheritDoc}
     */
    public FileMoverException(final String message, final Throwable cause)
    {
        super(message, cause);
    }


    /**
     * {@inheritDoc}
     */
    public FileMoverException(final Throwable cause)
    {
        super(cause);
    }


    /**
     * {@inheritDoc}
     */
    public FileMoverException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
