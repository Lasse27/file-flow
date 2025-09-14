package listener;

import model.listener.Listener;
import model.listener.ListenerEvent;
import model.listener.ProgressEvent;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ConsoleListener implements Listener
{

    public static final String RESET = "\u001B[0m";

    public static final String RED = "\u001B[31m";

    public static final String GREEN = "\u001B[32m";

    public static final String YELLOW = "\u001B[33m";

    public static final String BLUE = "\u001B[34m";


    @SuppressWarnings("SimpleDateFormatWithoutLocale")
    private static final DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
            .withLocale(Locale.getDefault())
            .withZone(ZoneId.systemDefault());


    /**
     * {@inheritDoc}
     */
    @Override
    public void onStart(final ListenerEvent event)
    {
        final String timestamp = formatter.format(event.getTimestamp());
        System.out.printf("%s %s - %s%s%n", GREEN, timestamp, event.getMessage(), RESET);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void onProgress(final ProgressEvent event)
    {
        final String timestamp = formatter.format(event.getTimestamp());
        System.out.printf("%s %s | %s - %s%s%n", BLUE, timestamp, event.getProgress(), event.getMessage(), RESET);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void onCancel(final ListenerEvent event)
    {
        final String timestamp = formatter.format(event.getTimestamp());
        System.out.printf("%s %s - %s%s%n", YELLOW, timestamp, event.getMessage(), RESET);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void onEnd(final ListenerEvent event)
    {
        final String timestamp = formatter.format(event.getTimestamp());
        System.out.printf("%s %s - %s%s%n", GREEN, timestamp, event.getMessage(), RESET);
    }
}
