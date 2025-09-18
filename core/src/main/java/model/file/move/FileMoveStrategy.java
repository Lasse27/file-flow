package model.file.move;

import model.file.conflict.FileAction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFileAttributes;

/**
 * Represents a strategy for moving a collection of files to a specified target directory.
 * This functional interface allows customized implementations for moving files with optional rules and configurations.
 */
@FunctionalInterface
public interface FileMoveStrategy
{

    static PosixFileAttributes readPosixFileAttributes(final Path sourcePath) throws IOException
    {
        PosixFileAttributes posixAttrs = null;
        try
        {
            posixAttrs = Files.readAttributes(sourcePath, PosixFileAttributes.class);
        }
        catch (final UnsupportedOperationException ignored)
        {
            // Nicht-POSIX-Systeme ueberspringen
        }
        return posixAttrs;
    }


    static void restoreFileAttributes(final Path path, final BasicFileAttributes basicFileAttributes, final PosixFileAttributes posixFileAttributes) throws IOException
    {
        if (posixFileAttributes != null)
        {
            Files.setPosixFilePermissions(path, posixFileAttributes.permissions());
            Files.setOwner(path, posixFileAttributes.owner());
        }

        Files.setLastModifiedTime(path, basicFileAttributes.lastModifiedTime());
    }

    /**
     * Moves a file from the specified source path to the target path, optionally restoring file
     * attributes such as permissions, ownership, and modification time based on the implementation.
     * If an error occurs during the move operation, it returns an unresolved file action.
     *
     * @param sourceFile      the path of the source file to be moved; must not be null and should point to an existing file.
     * @param targetDirectory the path of the target file where the source file is to be moved; must not be null.
     * @return a {@code FileAction} representing the result of the operation:
     * - {@code RESOLVED} if the file was successfully moved.
     * - {@code UNRESOLVED} if the move operation failed.
     */
    FileAction move(final Path sourceFile, final Path targetDirectory);
}
