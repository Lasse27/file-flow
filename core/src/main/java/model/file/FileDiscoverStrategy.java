package model.file;

import exception.FileDiscoverException;
import model.listener.Listener;

import java.nio.file.Path;
import java.util.List;

/**
 * Represents a strategy for discovering and retrieving file paths from a given source directory or path.
 * This is a functional interface that can be used as a target for lambda expressions or method references.
 */
@FunctionalInterface
public interface FileDiscoverStrategy
{
    /**
     * Discovers file paths from the specified source path and notifies a listener about the discovery process.
     *
     * @param sourcePath the path to the source directory or file from which file paths need to be discovered.
     *                   Must not be null and must refer to a valid file system location.
     * @param listener   the listener to monitor and handle events during the discovery process.
     *                   It provides callbacks for start, progress, cancellation, and end of the process.
     * @return a list of {@code Path} objects representing the discovered file paths.
     * Returns an empty list if no files were discovered.
     * @throws FileDiscoverException if an error occurs during the discovery process, such as an invalid source path
     *                               or lack of necessary permissions.
     */
    List<Path> discover(final Path sourcePath, final Listener listener) throws FileDiscoverException;
}
