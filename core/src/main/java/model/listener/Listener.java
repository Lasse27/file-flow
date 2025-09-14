package model.listener;

import com.sun.source.util.TaskEvent;

/**
 * A listener interface for monitoring the lifecycle of a process or task.
 * <p>
 * This interface provides default (no-op) implementations for all methods, allowing implementers to override only the callbacks they are interested in.
 */
public interface Listener
{
    /**
     * Invoked when the process or task starts.
     *
     * @implNote The default implementation does nothing.
     */
    default void onStart(final ListenerEvent event) {}

    /**
     * Invoked to report progress of the process or task.
     *
     * @param event Progress as an event
     * @implNote The default implementation does nothing.
     */
    default void onProgress(final ProgressEvent event) {}

    /**
     * Invoked when the process or task is cancelled.
     *
     * @implNote The default implementation does nothing.
     */
    default void onCancel(final ListenerEvent event) {}

    /**
     * Invoked when the process or task ends successfully or completes execution.
     *
     * @implNote The default implementation does nothing.
     */
    default void onEnd(final ListenerEvent event) {}
}
