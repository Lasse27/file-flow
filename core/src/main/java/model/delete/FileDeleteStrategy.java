package model.delete;

import model.conflict.FileMove;
import model.listener.Listener;

import java.nio.file.Path;

/**
 * A functional interface representing a strategy for deleting files or directories.
 * It provides a single abstract method to implement custom file deletion logic.
 */
@FunctionalInterface
public interface FileDeleteStrategy
{
    /**
     * Deletes the specified file or directory located at the given path.
     *
     * @param targetPath the path of the file or directory to be deleted.
     * @param listener the listener to monitor the deletion process, providing callbacks for start, progress, cancellation, or completion events.
     * @return a {@link FileMove} representing the result of the delete operation, including details on whether the operation was isResolved successfully.
     */
    FileDeletion delete(final Path targetPath, final Listener listener);
}
