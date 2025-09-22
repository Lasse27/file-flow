package model.move;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.conflict.FileMove;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFileAttributes;

/**
 * A concrete implementation of {@link FileMoveStrategy} that provides functionality
 * to move files into a flat structure at the target directory.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlatMoveStrategy implements FileMoveStrategy
{
    @Builder.Default
    private boolean restoreAttributes = true;


    /**
     * {@inheritDoc}
     */
    @Override
    public FileMove move(final Path sourceFile, final Path targetDirectory)
    {
        // Check if the target directory exists
        if (!Files.exists(targetDirectory))
        {
            return FileMove.UNRESOLVED(sourceFile, null);
        }

        // Get the target path of the file by combining the filename with the target directory
        final Path targetPath = targetDirectory.resolve(sourceFile.getFileName());

        try
        {
            // get attrs
            final BasicFileAttributes attributes = Files.readAttributes(sourceFile, BasicFileAttributes.class);
            final PosixFileAttributes posixAttrs = FileMoveStrategy.readPosixFileAttributes(sourceFile);

            // Skip move if it's the same file
            if (sourceFile.toAbsolutePath().equals(targetPath.toAbsolutePath()))
            {
                return FileMove.RESOLVED(sourceFile, targetPath);
            }

            // move file
            Files.move(sourceFile, targetPath);
            if (this.restoreAttributes)
            {
                FileMoveStrategy.restoreFileAttributes(targetDirectory, attributes, posixAttrs);
            }
        }
        catch (final IOException exception)
        {
            return FileMove.UNRESOLVED(sourceFile, targetPath);
        }

        return FileMove.RESOLVED(sourceFile, targetPath);
    }
}


