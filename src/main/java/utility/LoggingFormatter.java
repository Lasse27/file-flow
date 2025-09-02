package utility;


import java.util.logging.Formatter;
import java.util.logging.LogRecord;


/**
 * A custom logging formatter that extends the {@link Formatter} class. It is intended to be used with logging handlers to customize the appearance of log messages within a
 * Java logging framework.
 *
 * @author Lasse27
 * @since 2025-09-02
 */
@SuppressWarnings ("ClassWithoutLogger")
public class LoggingFormatter extends Formatter
{
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String format (final LogRecord record)
	{
		return record.getInstant() + " | " + record.getLevel().toString().substring(0, 4) + " | " + record.getMessage() + "\n";
	}
}
