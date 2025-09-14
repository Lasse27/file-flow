package listener;

import model.listener.Listener;
import model.listener.ListenerEvent;
import model.listener.ProgressEvent;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ConsoleListener implements Listener
{

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
        System.out.printf("%s - %s: %s%n", timestamp, event.getTaskId(), event.getMessage());
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void onProgress(final ProgressEvent event)
    {
        final String timestamp = formatter.format(event.getTimestamp());
        System.out.printf("%s - %s: + %s - %s%n", timestamp, event.getTaskId(), event.getProgress(), event.getMessage());
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void onCancel(final ListenerEvent event)
    {
        final String timestamp = formatter.format(event.getTimestamp());
        System.out.printf("%s - %s: %s%n", timestamp, event.getTaskId(), event.getMessage());
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void onEnd(final ListenerEvent event)
    {
        final String timestamp = formatter.format(event.getTimestamp());
        System.out.printf("%s - %s: %s%n", timestamp, event.getTaskId(), event.getMessage());
    }
}
