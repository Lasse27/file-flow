package model.file;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * Represents a strategy for moving a collection of files to a specified target directory.
 * This functional interface allows customized implementations for moving files with optional rules and configurations.
 */
@FunctionalInterface
public interface FileMoveStrategy
{
    /**
     * Moves a list of files to a specified target directory, applying optional file move configurations.
     *
     * @param sourceFiles     a list of file paths to be moved; must not be null or empty.
     *                        Each path should point to an existing source file.
     * @param targetDirectory the destination directory where the files will be moved; must not be null and should exist.
     * @param rules           optional configurations that control the behavior of the move operation, such as handling
     *                        duplicate files or other specific rules.
     * @return a map of {@code Path} objects representing the new locations of the moved files.
     * Returns an empty list if no files were moved successfully.
     */
    Map<Path, Path> move(final List<Path> sourceFiles, final Path targetDirectory, FileMoveRule... rules);
}
