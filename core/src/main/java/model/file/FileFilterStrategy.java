package model.file;

import java.nio.file.Path;

/**
 * A functional interface that defines a criterion for filtering file paths.
 * Implementations of this interface evaluate whether a given file path satisfies specific conditions.
 */
@FunctionalInterface
public interface FileFilterStrategy
{
    /**
     * Evaluates whether the given file path satisfies certain conditions defined by the implementation.
     *
     * @param file the file path to be evaluated; must not be null and should point to a valid location.
     * @return {@code true} if the file meets the criteria specified by the implementation, {@code false} otherwise.
     */
    boolean accept(final Path file);
}
