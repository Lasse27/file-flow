package exception;

/**
 * An exception that represents validation errors occurring during the execution
 * of procedures or processes.
 */
public class ProcedureValidatorException extends RuntimeException
{
    /**
     * {@inheritDoc}
     */
    public ProcedureValidatorException()
    {
    }


    /**
     * {@inheritDoc}
     */
    public ProcedureValidatorException(final String message)
    {
        super(message);
    }


    /**
     * {@inheritDoc}
     */
    public ProcedureValidatorException(final String message, final Throwable cause)
    {
        super(message, cause);
    }


    /**
     * {@inheritDoc}
     */
    public ProcedureValidatorException(final Throwable cause)
    {
        super(cause);
    }


    /**
     * {@inheritDoc}
     */
    protected ProcedureValidatorException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
