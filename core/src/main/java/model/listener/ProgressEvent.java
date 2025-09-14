package model.listener;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Represents an event that provides progress updates for a specific task or process.
 * This event can be used to notify listeners about the progress state of a process or operation.
 */
@Getter
@SuperBuilder
public class ProgressEvent extends ListenerEvent
{

    /**
     * Represents the progress of a task or operation, expressed as a percentage (0.0 to 100.0).
     */
    private final double progress;
}
