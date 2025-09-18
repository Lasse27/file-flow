package model.listener;

import lombok.Builder;
import lombok.Data;
import model.shared.Registrable;

import java.util.Arrays;

/**
 * A collection of {@link Listener} objects that invokes the appropriate methods on each listener in the collection.
 * <br>
 * This class implements the {@link Listener} interface and provides functionality to manage a group of listeners.
 */
@Data
@Builder
public class ListenerCollection implements Listener, Registrable<Listener>
{

    /**
     * A constant representing the default empty array of {@link Listener} instances.
     */
    public static final Listener[] DEFAULT = new Listener[0];

    /**
     * An array of {@link Listener} objects representing the current collection of listeners managed by this class.
     */
    @Builder.Default
    private Listener[] listeners = DEFAULT;


    /**
     * Invokes the {@code onStart} method on each listener in the collection. This method sequentially calls the {@code onStart} method of all
     * {@link Listener} instances contained in the {@code listeners} array.
     */
    @Override
    public void onStart(final ListenerEvent event)
    {
        Arrays.stream(this.listeners).forEach(listener -> listener.onStart(event));
    }


    /**
     * Notifies all registered listeners of a progress update. This method sequentially invokes the {@code onProgress} method
     * for each {@link Listener} instance in the {@code listeners} array,
     * passing the given progress value.
     *
     * @param event the current progress value to be passed to each listener
     */
    @Override
    public void onProgress(final ProgressEvent event)
    {
        Arrays.stream(this.listeners).forEach(listener -> listener.onProgress(event));
    }


    /**
     * Invokes the {@code onCancel} method on each listener in the collection. This method iterates through all {@link Listener} instances contained in the
     * {@code listeners} array and calls their {@code onCancel} method sequentially.
     */
    @Override
    public void onCancel(final ListenerEvent event)
    {
        Arrays.stream(this.listeners).forEach(listener -> listener.onCancel(event));
    }


    /**
     * Invokes the {@code onEnd} method on each listener in the collection. This method sequentially calls the {@code onEnd} method of all
     * {@link Listener} instances contained in the {@code listeners} array.
     */
    @Override
    public void onEnd(final ListenerEvent event)
    {
        Arrays.stream(this.listeners).forEach(listener -> listener.onEnd(event));
    }


    @Override
    public void register(final Listener listener)
    {
        this.listeners = Arrays.copyOf(this.listeners, this.listeners.length + 1);
        this.listeners[this.listeners.length - 1] = listener;
    }


    @Override
    public void unregister(final Listener listener)
    {
        this.listeners = Arrays.stream(this.listeners)
                .filter(currentListener -> currentListener != listener)
                .toArray(Listener[]::new);
    }
}
