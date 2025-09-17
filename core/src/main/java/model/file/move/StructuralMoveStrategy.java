package model.file.move;

import lombok.Builder;
import lombok.Data;
import model.file.conflict.FileAction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFileAttributes;

/**
 * A concrete implementation of {@link FileMoveStrategy} that organizes files into a directory
 * structure at the target location that mirrors the structure relative to the specified source directory.
 * <br>
 * It computes the relative path of the source file with respect to its parent directory and
 * appends this relative path to the target directory, ensuring the structural hierarchy is preserved.
 */
@Data
@Builder
public class StructuralMoveStrategy implements FileMoveStrategy
{

    private final Path sourceDirectory;

    @Builder.Default
    private final boolean restoreAttributes = true;


    /**
     * {@inheritDoc}
     */
    @Override
    public FileAction move(final Path sourceFile, final Path targetDirectory)
    {
        // Check if the target directory exists
        if (!Files.exists(targetDirectory))
        {
            return FileAction.UNRESOLVED(sourceFile, null);
        }

        // Get the relative path to source directory
        final Path relativeSourceDirectory = this.sourceDirectory.relativize(sourceFile);
        final Path targetPath = targetDirectory.resolve(relativeSourceDirectory);

        try
        {
            // get attrs
            final BasicFileAttributes attributes = Files.readAttributes(sourceFile, BasicFileAttributes.class);
            final PosixFileAttributes posixAttrs = FileMoveStrategy.readPosixFileAttributes(sourceFile);

            // Skip move if it's the same file
            if (sourceFile.toAbsolutePath().equals(targetPath.toAbsolutePath()))
            {
                return FileAction.RESOLVED(sourceFile, targetPath);
            }

            // move file
            Files.createDirectories(targetPath.getParent());
            Files.move(sourceFile, targetPath);
            if (this.restoreAttributes)
            {
                FileMoveStrategy.restoreFileAttributes(targetDirectory, attributes, posixAttrs);
            }
        }
        catch (final IOException exception)
        {
            return FileAction.UNRESOLVED(sourceFile, targetPath);
        }

        return FileAction.RESOLVED(sourceFile, targetPath);
    }
}
