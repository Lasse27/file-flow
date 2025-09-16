package model.file.move;

import model.file.conflict.FileAction;

import java.nio.file.Path;

/**
 * Represents a strategy for moving a collection of files to a specified target directory.
 * This functional interface allows customized implementations for moving files with optional rules and configurations.
 */
@FunctionalInterface
public interface FileMoveStrategy
{
    /**
     * Moves a file from the specified source path to the target path, optionally restoring file
     * attributes such as permissions, ownership, and modification time based on the implementation.
     * If an error occurs during the move operation, it returns an unresolved file action.
     *
     * @param sourceFile the path of the source file to be moved; must not be null and should point to an existing file.
     * @param targetDirectory the path of the target file where the source file is to be moved; must not be null.
     * @return a {@code FileAction} representing the result of the operation:
     * - {@code RESOLVED} if the file was successfully moved.
     * - {@code UNRESOLVED} if the move operation failed.
     */
    FileAction move(final Path sourceFile, final Path targetDirectory);
}
