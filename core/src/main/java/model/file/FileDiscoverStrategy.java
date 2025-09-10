package model.file;

import exception.FileDiscoverException;

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
     * Discovers and retrieves a list of file paths from the specified source path.
     *
     * @param sourcePath the root path from which file discovery will begin. This path must not be null and should point to a valid location.
     * @return a list of {@link Path} objects representing the discovered files.
     * @throws FileDiscoverException if an error occurs during file discovery, such as inaccessible directories or invalid paths.
     */
    List<Path> discover(Path sourcePath) throws FileDiscoverException;
}
