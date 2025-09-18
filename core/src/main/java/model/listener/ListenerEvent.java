package model.listener;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

/**
 * Represents an event used to communicate information about the state of a task or process.
 * This class encapsulates task-specific details such as a unique identifier, an informational message, and a timestamp.
 */
@Data
@SuperBuilder
public class ListenerEvent
{
    private final String message;

    private final Instant timestamp = Instant.now();
}
